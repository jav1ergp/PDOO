
require 'io/console'
require_relative 'directions'

module UI

  class TextUI

    #https://gist.github.com/acook/4190379
    def read_char
      STDIN.echo = false
      STDIN.raw!

      input = STDIN.getc.chr
      if input == "\e"
        input << STDIN.read_nonblock(3) rescue nil
        input << STDIN.read_nonblock(2) rescue nil
      end
    ensure
      STDIN.echo = true
      STDIN.cooked!

      return input
    end

    def next_move
      print "Where? "
      got_input = false
      while (!got_input)
        c = gets.chomp
        case c
          when "W"
            puts "UP ARROW"
            output = Irrgarten::Directions::UP
            got_input = true
          when "S"
            puts "DOWN ARROW"
            output = Irrgarten::Directions::DOWN
            got_input = true
          when "D"
            puts "RIGHT ARROW"
            output = Irrgarten::Directions::RIGHT
            got_input = true
          when "A"
            puts "LEFT ARROW"
            output = Irrgarten::Directions::LEFT
            got_input = true
          when "\u0003"
            puts "CONTROL-C"
            got_input = true
            exit(1)
          else
            #Error
        end
      end
      output
    end

    def show_game(game_state)
      puts   "Current player: " + game_state.current_player + "\n" +
             "Winner?: " + game_state.winner.to_s + "\n" +
             "Players: " + game_state.players.to_s + "\n" +
             "Monsters: " + game_state.monsters.to_s + "\n" +
             game_state.labyrinthv +
             "Log:\n" + game_state.log + "\n"
    end

  end # class   

end # module   


