# Towers of Hanoi

Menu page with 3 options:
1. Start Game
2. Help
3. Exit

## Start Game
User enters number of disks n (4 <= n <= 9).
Game starts immediately after input.

## State Representation
Each tower is represented using a Stack.
The full game state consists of 3 stacks (Tower 1, Tower 2, Tower 3).

## Rules
- Only one disk can be moved at a time.
- Only the top disk of a tower can be moved.
- A larger disk cannot be placed on a smaller disk.

## User Input

```java
a b (1 <= a, b <= 3)
```
Here disk from 'a' (if exists obviously) will be moved to disk 'b'.

## Goal 

Move disks from tower 1 to tower 3
