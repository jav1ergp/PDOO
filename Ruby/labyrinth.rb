# frozen_string_literal: true
require_relative 'directions'
require_relative 'orientation'
require_relative 'dice'
require_relative 'monster'
require_relative 'player'

module Irrgarten
  class Labyrinth
    @@BLOCK_CHAR = 'X'
    @@EMPTY_CHAR = '-'
    @@MONSTER_CHAR = 'M'
    @@COMBAT_CHAR = 'C'
    @@EXIT_CHAR = 'E'
    @@ROW = 0
    @@COL = 1

    def initialize(n_rows, n_cols, exit_row, exit_cols)
      @n_rows = n_rows
      @n_cols = n_cols
      @exit_row = exit_row
      @exit_col = exit_cols

      @labyrinth = Array.new(@n_rows) { Array.new(@n_cols) } # el {} dice que en cada fila hay un array de columnas que se inicializa al empty
      @players = Array.new(@n_rows) { Array.new(@n_cols) }
      @monsters = Array.new(@n_rows) {Array.new(@n_cols)}

      @labyrinth.map do |row|
        row.map!{@@EMPTY_CHAR}
      end

      @labyrinth[@exit_row][@exit_col] = @@EXIT_CHAR
    end

    def have_a_winner
      @players[@exit_row][@exit_col]
    end

    def to_s
      s = "L[#{@row}, #{@col}, #{@exit_row}, #{@exit_col}]\n"

      for i in 0...@n_rows
        s += "["
        for j in 0...@n_cols
          s += "#{@labyrinth[i][j]}, "
        end
        s += "]\n"
      end
      s
    end

    def add_monster(row, col, monster)
      if row >= 0 && row < @n_rows && col >= 0 && col < @n_cols
        if @labyrinth[row][col] == @@EMPTY_CHAR
          @monsters[row][col] = monster
          @labyrinth[row][col] = @@MONSTER_CHAR
        end
      end
    end

    def pos_ok(row, col)
      if (row >= 0 && row < @n_rows) && (col >= 0 && col < @n_cols)
        true
      else
        false
      end
    end

    def empty_pos(row, col)
      if pos_ok(row, col)
        @labyrinth[row][col] == @@EMPTY_CHAR
      else
        false
      end
    end

    def monster_pos(row, col)
      if pos_ok(row, col)
        @labyrinth[row][col] == @@MONSTER_CHAR
      else
        false
      end
    end

    def exit_pos(row, col)
      if pos_ok(row, col)
        row == @exit_row && col == @exit_col
      else
        false
      end
    end

    def combat_pos(row, col)
      if pos_ok(row, col)
        @labyrinth[row][col] == @@COMBAT_CHAR
      else
        false
      end
    end

    def can_step_on(row, col)
      if pos_ok(row, col)
        empty_pos(row, col) || monster_pos(row, col) || exit_pos(row, col)
      else
        false
      end
    end

    def update_old_pos(row, col)
      if pos_ok(row, col)
        if combat_pos(row, col)
          @labyrinth[row][col] = @@MONSTER_CHAR
        else
          @labyrinth[row][col] = @@EMPTY_CHAR
        end
      end
    end

    def dir2_pos(row, col, direction)
      n_pos = []

      case direction
      when Directions::LEFT
        n_pos[0] = row
        n_pos[1] = col - 1
      when Directions::RIGHT
        n_pos[0] = row
        n_pos[1] = col + 1
      when Directions::UP
        n_pos[0] = row - 1
        n_pos[1] = col
      when Directions::DOWN
        n_pos[0] = row + 1
        n_pos[1] = col
      else
        "NOT VALID"
      end

      n_pos
    end

    def random_empty_pos
      random_pos = []

      random_pos[0] = Dice.random_pos(@n_rows)
      random_pos[1] = Dice.random_pos(@n_cols)

      while !empty_pos(random_pos[0], random_pos[1])
        random_pos[0] = Dice.random_pos(@n_rows)
        random_pos[1] = Dice.random_pos(@n_cols)
      end

      random_pos
    end

    def add_block(orientation, start_row, start_col, length)
      inc_row = 0
      inc_col = 0

      if orientation == Orientation::VERTICAL
        inc_row = 1
      else
        inc_col = 1
      end

      row = start_row
      col = start_col

      while pos_ok(row, col) && empty_pos(row, col) && length > 0
        @labyrinth[row][col] = @@BLOCK_CHAR
        length -= 1
        row += inc_row
        col += inc_col
      end
    end

    def put_player(direction, player)
      old_row = player.row
      old_col = player.col
      new_pos = dir2_pos(old_row, old_col, direction)
      monster = put_player_2d(old_row, old_col, new_pos[@@ROW], new_pos[@@COL], player)
    end

    def put_player_2d(old_row, old_col, row, col, player)
      output = nil
      if can_step_on(row, col)
        if pos_ok(old_row, old_col)
          p = @players[old_row][old_col]
          if p == player
            update_old_pos(old_row, old_col)
            @players[old_row][old_col] = nil
          end
        end

        monster_pos = monster_pos(row, col)

        if monster_pos
          @labyrinth[row][col] = @@COMBAT_CHAR
          output = @monsters[row][col]
        else
          number = player.number
          @labyrinth[row][col] = number
        end

        @players[row][col] = player
        player.set_pos(row, col)
      end
      output
    end

    def valid_moves(row, col)
      output = []
      if can_step_on(row + 1, col)
        output << Directions::DOWN
      end
      if can_step_on(row - 1, col)
        output << Directions::UP
      end
      if can_step_on(row, col + 1)
        output << Directions::RIGHT
      end
      if can_step_on(row, col - 1)
        output << Directions::LEFT
      end
      output
    end

    def spread_players(players)
      players.each do |p|
        pos = random_empty_pos
        put_player_2d(-1, -1, pos[@@ROW], pos[@@COL], p)
      end
    end

    def set_fuzzy_player(player)
      @players[player.row][player.col] = player
    end
  end
end
