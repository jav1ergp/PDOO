# frozen_string_literal: true
require_relative 'player'
require_relative 'Dice'

module Irrgarten
  class FuzzyPlayer < Player
    def initialize(other)
      copy(other)
    end

    def move(direction, valid_moves)
      preference = super.move(direction, valid_moves)
      Dice.next_step(preference, valid_moves, @intelligence)
    end

    def attack
      weapons_dmg = super.attack
      fuerza_dmg = Dice.intensity(super.get_strength)
      weapons_dmg + fuerza_dmg
    end

    def to_s
      "Fuzzy " + super.to_s
    end

    def defensive_energy
      shield_dmg = super.defensive_energy
      intelligence_dmg = Dice.intensity(super.get_intelligence)
      shield_dmg + intelligence_dmg
    end

    protected :defensive_energy
  end
end