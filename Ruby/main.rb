# frozen_string_literal: true
require_relative 'game'
require_relative 'controller'
require_relative 'textUI'

include(Irrgarten)
include(UI)
include(Control)

module Play
  class Main
    def main
      game = Game.new(2)
      ui = TextUI.new
      controller = Controller.new(game, ui)
      controller.play
    end
  end
end

Play::Main.new.main