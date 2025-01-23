# FLAPPY BIRD: 2049



#### Description: 

This project is a modern reimagining of the classic Flappy Bird game, with enhanced graphics, engaging gameplay mechanics, and a dystopian narrative twist. The player controls a bird that navigates through an endless series of obstacles in the form of pipes, aiming to achieve the highest score before colliding with any pipe or falling out of bounds.

#### Key Features
1. Narrative Introduction:
   - The bird is transported to the year 2049, a dystopian future.
   - The game begins with a backstory displayed on the start screen, immersing the player in a unique storyline.

2. Game Mechanics:
   - Dynamic Gravity: The bird is affected by gravity and must flap to stay airborne.
   - Pipe Generation: Pipes with random spacing are periodically introduced to challenge the player's reflexes.
   - Scoring: Points are awarded for successfully passing through each set of pipes.

3. Graphics:
   - A visually appealing background representing the dystopian future.
   - High-quality sprites for the bird and pipes, with smooth animations.

4. Sound Design:
   - Background music (BGM) loops continuously to enhance the gameplay experience.
   - Potential for adding sound effects for flapping, collisions, and scoring.

5. Game States:
   - Start: Displays an introductory story and prompts the player to begin.
   - Playing: Active gameplay state where the bird navigates the pipes.
   - Game Over: Displays the final score and allows for restarting the game.

6. User Interaction:
   - Controlled using the spacebar for flapping.
   - Easy restart functionality upon game over.

#### Technical Details
1. Platform:
   - Developed in Java using Swing and AWT libraries for GUI rendering.
   - Supports cross-platform execution.

2. Design Patterns:
   - Object-Oriented Approach: Separate classes for `Bird` and `Pipe` encapsulate game object properties and behavior.
   - Timer-based Game Loop: Ensures smooth gameplay at 60 frames per second.

3. Collision Detection:
   - Pixel-perfect bounding box collision detection ensures accurate gameplay interactions.

4. Dynamic Difficulty:
   - Pipes are placed at random heights, with varying gaps to maintain a challenging yet engaging experience.

5. Error Handling:
   - Exception handling ensures smooth loading of assets such as images and audio files.

6. Expandable Design:
   - Easily extendable for new features like additional levels, power-ups, or multiplayer modes.

#### Potential Enhancements
1. Advanced Features:
   - Add power-ups like shields or slow-motion effects.
   - Introduce varying pipe speeds and patterns for increased difficulty.

2. User Feedback:
   - Integrate sound effects for scoring and collisions.
   - Add animations for game over and start transitions.

3. Aesthetic Improvements:
   - Transition animations between game states.
   - Multiple background themes to reflect the dystopian narrative.

4. Social Features:
   - Include a leaderboard for competitive gameplay.
   - Enable score sharing on social platforms.

#### Conclusion
This project blends nostalgia with modern design principles, offering a visually captivating and engaging gaming experience. With its dystopian narrative and polished mechanics, it stands out as a fresh take on the classic Flappy Bird game.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.



