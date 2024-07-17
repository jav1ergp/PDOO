# frozen_string_literal: true
require_relative 'orientation'
require_relative 'labyrinth'
require_relative 'player'
require_relative 'dice'
require_relative 'game_character'
require_relative 'labyrinth'
require_relative 'game_state'
require_relative 'monster'

module Irrgarten
  class Game

    @@MAX_ROUNDS = 10

    def initialize(nplayers)
      @log = ''
      @players = []
      @monsters = []

      for i in 1..nplayers
        number = i.to_s
        player = Player.new(number, Dice.random_intelligence, Dice.random_strength)
        @players << player
      end

      @current_player_index = Dice.who_starts(nplayers)
      @current_player = @players[@current_player_index]

      @labyrinth = Labyrinth.new(4, 4, 3, 3)
      configure_labyrinth
      @labyrinth.spread_players(@players)
    end

    def finished
      @labyrinth.have_a_winner
    end

    def next_step(preferred_direction)
      log = ''
      dead = @current_player.dead

      if !dead
        direction = actual_direction(preferred_direction)
        if direction != preferred_direction
          log_player_no_orders
        end
        monster = @labyrinth.put_player(direction, @current_player)
        if monster == nil
          log_no_monster
        else
          winner = combat(monster)
          manage_reward(winner)
        end
      else
        manage_resurrection
      end
      end_game = finished
      if !end_game
        next_player
      end
      end_game
    end

    def game_state
      labyrinth_state = @labyrinth.to_s
      player_state = ''
      for player in @players
        player_state += player.to_s
      end

      monster_state = ''
      for monster in @monsters
        monster_state += monster.to_s
      end

      game_state = GameState.new(labyrinth_state, player_state, monster_state, @current_player.number, finished, @log)
    end

    def configure_labyrinth
      @labyrinth.add_block(Orientation::VERTICAL, 3, 2, 1)
      @labyrinth.add_block(Orientation::HORIZONTAL, 1, 2, 2)

      monster1 = Monster.new('M1', Dice.random_intelligence, Dice.random_strength)
      monster2 = Monster.new('M2', Dice.random_intelligence, Dice.random_strength)

      @labyrinth.add_monster(Dice.random_pos(4), Dice.random_pos(4), monster1)
      @labyrinth.add_monster(Dice.random_pos(4), Dice.random_pos(4), monster2)
      @monsters << monster1
      @monsters << monster2
    end

    def next_player
      if @current_player_index < @players.size - 1
        @current_player_index += 1
        @current_player = @players[@current_player_index]
      else
        @current_player_index = 0
        @current_player = @players[@current_player_index]
      end
    end

    def actual_direction(preferred_direction)
      current_row = @current_player.row
      current_col = @current_player.col
      valid_moves = []
      valid_moves = @labyrinth.valid_moves(current_row, current_col)
      output = @current_player.move(preferred_direction, valid_moves)
    end

    def combat(monster)
      rounds = 0
      winner = GameCharacter::PLAYER
      player_attack = @current_player.attack
      lose = monster.defend(player_attack)

      while !lose && rounds < @@MAX_ROUNDS
        winner = GameCharacter::MONSTER
        rounds += 1
        monster_attack = monster.attack
        lose = @current_player.defend(monster_attack)
        if !lose
          player_attack = @current_player.attack
          lose = monster.defend(player_attack)
          winner = GameCharacter::PLAYER
        end
      end
      log_rounds(rounds, @@MAX_ROUNDS)
      winner
    end

    def manage_resurrection
      resurrect = Dice.resurrect_player

      if resurrect
        @current_player.resurrect
        log_resurrected
      else
        log_player_skip_turn
      end
    end

    def manage_reward(winner)
      if winner == GameCharacter::PLAYER
        @current_player.receive_reward
        log_player_won
      else
        log_monster_won
      end
    end

    def log_player_won
      @log = "The player " + @current_player_index.to_s + " has won the combat.\n"
    end

    def log_monster_won
      @log = "The monster has won the combat.\n"
    end

    def log_resurrected
      @log = "The player " + @current_player_index.to_s + " has resurrected.\n"
    end

    def log_player_skip_turn
      @log = "The player " + @current_player_index.to_s + " has lost turn for being dead.\n"
    end

    def log_player_no_orders
      @log = "The player " + @current_player_index.to_s + " did not follow the human player's instructions (not possible).\n"
    end

    def log_no_monster
      @log = "The player " + @current_player_index.to_s + " moved to an empty cell or could not move at all.\n"
    end

    def log_rounds(rounds, max)
      @log = "There have been #{rounds} of #{max} rounds of combat.\n"
    end
  end
end