# Towers of Hanoi

A Java console-based graphical implementation of the classic Towers of Hanoi puzzle. 

This repository is split into two distinct branches:
- **`CLI-version`**: Text-based interactive command-line implementation.
- **`GUI-version`**: Graphical user interface implementation (current branch).

## How to Play (CLI Version)

### Compilation & Execution
The project uses a standard `Makefile` for compilation and execution. Ensure you have the Java Development Kit (JDK) installed and then run:

```bash
make run
```

*Note: You can clean up the compiled binary files at any time using `make clean`.*

### Game Flow & Controls
1. Once the game starts, you will be asked to input the **number of disks** to play with.
2. An ASCII-art representation of the three towers will appear on your screen.
3. On every turn, you will be sequentially prompted to input:
   - The **rod to take a disk from**
   - The **rod to place the disk on**

For inputting your moves, use the following mapping:
- `1` = Left Rod
- `2` = Middle Rod
- `3` = Right Rod

### Rules
1. Only **one disk** can be moved at a time.
2. Only the **topmost disk** of a rod can be moved.
3. A **larger disk** cannot be placed on top of a **smaller disk**.

### Goal
Successfully relocate the entire stack of disks from the first rod to the last rod (Rod 3). Once solved, you will be congratulated and asked if you want to play another round!

## Architecture Details
Under the hood, the game relies on basic data structures: each of the three rods is implemented as a `Stack<Integer>`. Pushing and popping disks from these stacks allows the game to efficiently validate moves checking that no disk is pushed onto a stack possessing a smaller top element.
