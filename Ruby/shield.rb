# frozen_string_literal: true
require_relative 'dice'
require_relative 'combat_element'
module Irrgarten
  class Shield < Combat_element
    def protect
      produce_effect
    end

    def to_s
      "S[" + super # cadenas
    end
  end
end
