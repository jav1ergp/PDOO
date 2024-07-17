# frozen_string_literal: true
require_relative 'dice'
require_relative 'weapon'
require_relative 'shield'
require_relative 'labyrinth_character'

module Irrgarten
  class Player < Labyrinth_character
    @@MAX_WEAPONS = 2
    @@MAX_SHIELDS = 3
    @@INITIAL_HEALTH = 10
    @@HITS2LOSE = 3

    attr_reader :number, :row, :col

    def initialize(number, intelligence, strength)
      super('Player #' + number.to_s, intelligence, strength, @@INITIAL_HEALTH)
      @number = number
      @consecutive_hits = 0
      @weapons = []
      @shields = []
    end

    def self.copy(other)
      copy(other)
      @number = other.number
      @weapons = other.weapons.clone
      @shields = other.shields.clone
      @consecutive_hits = other.consecutive_hits
    end

    def resurrect
      @weapons.clear
      @shields.clear
      @health == @@INITIAL_HEALTH
      @consecutive_hits = 0
    end

    def set_pos(row, col)
      @row = row
      @col = col
    end

    def dead
      @health <= 0
    end

    def attack
      total_weapons_attack = @strength
      total_weapons_attack += sum_weapons
    end

    def defend(received_attack)
      manage_hit(received_attack)
    end

    def to_s
      "P Name: #{@name}, Intelligence: #{@intelligence}, Strength: #{@strength}, Health: #{@health}, Weapons: #{@weapons}, Shields: #{@shields}, Row: #{@row}, Col: #{@col}"
    end


    def new_weapon
      Weapon.new(Dice.weapon_power, Dice.uses_left)
    end

    def new_shield
      Shield.new(Dice.shield_power, Dice.uses_left)
    end

    def defensive_energy
      total_intelligence = @intelligence
      total_intelligence += sum_shields
    end

    def reset_hits
      @consecutive_hits = 0
    end

    def got_wounded
      @health -= -1
    end

    def inc_consecutive_hits
      @consecutive_hits += 1
    end

    def sum_weapons
      @weapons.sum(&:power)
    end

    def sum_shields
      @shields.sum(&:protect)
    end

    def manage_hit(received_attack)
      defense = defensive_energy
      if defense < received_attack
        got_wounded
        inc_consecutive_hits
      else
        reset_hits
      end

      if (@consecutive_hits == @@HITS2LOSE) || dead
        reset_hits
        lose = true
      else
        lose = false
      end
      lose
    end

    def move(direction, valid_moves)
      size = valid_moves.length
      contained = valid_moves.include? direction

      if size > 0 && !contained
        first_element = valid_moves.at(0)
      else
        direction
      end
    end

    def receive_reward
      w_reward = Dice.weapons_reward
      s_reward = Dice.shields_reward

      (1...w_reward).each do
        w_new = new_weapon
        receive_weapon(w_new)
      end

      (1...s_reward).each do
        s_new = new_shield
        receive_shield(s_new)
      end

      extra_health = Dice.health_reward
      @health += extra_health
    end

    def receive_weapon(w)
      @weapons.each do |wi|
        discard = wi.discard
        if discard
          @weapons.delete(wi)
        end
      end

      size = @weapons.length
      if size < @@MAX_WEAPONS
        @weapons << w
      end
    end

    def receive_shield(s)
      @shields.each do |si|
        discard = si.discard
        if discard
          @shields.delete(si)
        end
      end

      size = @shields.length
      if size < @@MAX_SHIELDS
        @shields << s
      end
    end
  end
end