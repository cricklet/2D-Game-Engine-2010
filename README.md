# 2D-Game-Engine-2010

![http://i.imgur.com/NEFm4VP.gif](http://i.imgur.com/NEFm4VP.gif)

This is a game engine I wrote in 2010. My goal was for it to be tobe as flexible as possible and allow simple, quick creation of new games.

GameEngine is the base engine. GravityEngine, PlatformerEngine, and RTSEngine all use the classes found within Game Engine. Plumber of the Living Dead, GravityGame, and ZombieRTS are all proof-of-concept games which show the GameEngine's capabilities.

Design Principles:
 * Entities: I didn't learn about using components to allow for pseudo-multiple-inheritance until after building this engine.
 * Inheritance: GameEngine->RTSEngine->ZombieRTS.
 * Generics: Almost every class uses generics. I wanted to be able to plug completely different styles of game into the same engine. Generics seemed like the way to do that at the time.
 * Global Events: any entity can send global events. These events can either be picked up by the game thread or by other entities. This is the main method of communication inside the game. I designed the game this way so entities wouldn't have to directly access functions in other entities.

[Demo video of a platformer built with this engine](http://www.youtube.com/watch?v=G8LXEY0m5IM)
