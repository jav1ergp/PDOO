# frozen_string_literal: true
require_relative 'Dice'

module Irrgarten
  class Combat_element
    def initialize(effect, uses)
      @effect = effect
      @uses = uses
    end

    def discard
      Dice.discard_element(@uses)
    end

    def to_s
      "#{@effect}, #{@uses}"
    end

    def produce_effect
      if @uses > 0
        effect = @effect
        @uses -= 1
      else
        effect = 0
      end
      effect
    end
  end
end