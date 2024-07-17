# frozen_string_literal: true
module Irrgarten

    class Labyrinth_character
      attr_reader :intelligence, :strength, :row, :col
      attr_accessor :health

    def initialize(name, intelligence, strength, health)
      @name = name
      @intelligence = intelligence
      @strength = strength
      @health = health
      @row = 0
      @col = 0
    end

    def self.copy(other)
      @name = other.name
      @intelligence = other.intelligence
      @strength = other.strength
      @health = other.health
      @row = other.row
      @col = other.col
    end

    def dead
      @health <= 0
    end

    def get_row
      @row
    end

    def get_col
      @col
    end

    def to_s
        "M Name: #{@name}, Intelligence: #{@intelligence}, Strength: #{@strength}, Health: #{@health}, Weapons: #{@weapons}, Shields: #{@shields}, Row: #{@row}, Col: #{@col}"
    end

    def attack
      raise NotImplementedError, "#{self.class} must implement this method"
    end

    def defend
      raise NotImplementedError, "#{self.class} must implement this method"
    end

      protected_methods

    def set_health(health)
      @health = health
    end

    def get_intelligence
      @intelligence
    end

    def get_strength
      @strength
    end

    def set_pos(row, col)
      @row = row
      @col = col
    end

    def got_wounded
      @health -=1
    end
  end
end
