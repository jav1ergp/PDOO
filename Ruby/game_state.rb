# frozen_string_literal: true
module Irrgarten
  class GameState
    attr_reader :labyrinthv, :players, :monsters, :current_player, :winner, :log

    def initialize(labyrinthv, players, monsters, current_player, winner, log)
      @labyrinthv = labyrinthv
      @players = players.to_s
      @monsters = monsters.to_s
      @current_player = current_player
      @winner = winner
      @log = log
    end
  end
end