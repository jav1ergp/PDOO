# frozen_string_literal: true
require_relative 'dice'
require_relative 'combat_element'
module Irrgarten
  class Weapon < Combat_element
    def attack
      produce_effect
    end

    def to_s
      "W[" + super # cadenas
    end
  end
end