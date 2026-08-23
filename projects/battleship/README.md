# Battleship (Browser Edition)

A simple, single-player Battleship game you can play directly in your browser: click a file, no install, no build step, no server.

## Why this exists

Skill goal: build a small, genuinely interactive, rules-based game that a complete stranger could open and play without any setup from me, and that explains its own rules to someone who has never played Battleship before.

## Stack

Plain HTML, CSS, and JavaScript in a single file. No frameworks, no dependencies, no build tool. Open `index.html` in any modern browser and it runs.

## Design notes

A few notes on the design choices behind this implementation:

- **Runs entirely in the browser.** The whole game is one HTML file with inline CSS and JavaScript, played with mouse clicks.
- **Includes a built-in rules and background panel.** The game explains what Battleship is, where it came from, and how to play, written for someone who has never played it before.
- **Own AI design.** The computer opponent uses a simple two-mode targeting strategy (random search, then hunt the four neighbors of a confirmed hit) implemented from scratch for this project.
- **Simplified, smaller ruleset.** An 8-by-8 grid with 4 ships instead of the classic 10-by-10 grid with 5 ships, to keep games short and approachable for a casual player.

## Rules

- The board is an 8-by-8 grid, columns A through H and rows 1 through 8.
- Each side has a fleet of 4 ships: a Carrier (4 squares), a Cruiser (3 squares), a Submarine (3 squares), and a Destroyer (2 squares).
- Ships are placed randomly and secretly at the start of each game, in a straight line, horizontally or vertically, and never overlap.
- You and the computer take turns. Click a square on the "Enemy Waters" board to fire at it.
- A hit turns the square red. A miss turns it light gray. Once every square of a ship has been hit, it is announced as sunk.
- The first side to sink the opponent's entire fleet wins.

Full background and rules are also written directly into the page itself, in a collapsible panel above the boards.

## How the computer opponent works

The computer fires at random squares until it lands a hit. Once it hits something, it queues up the four squares directly next to that hit and works through them before going back to firing at random, a simple "hunt around a confirmed hit" strategy rather than anything probability-based.

## Structure

```
battleship/
index.html   <- the entire game: markup, styling, and game logic in one file
```

## Run it

Open `index.html` in any browser. That's it, there is nothing to install or configure.

## Known limitations

This is a portfolio demo, not a production game: there is no persistence (refreshing the page starts a new game), no sound, no animation beyond simple color changes, and the computer AI is intentionally simple rather than optimized to play well.
