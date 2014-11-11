CountryGame
===========

Project Specification You develop a simple game in which the user will enter a country name, 
and the program searches the internet (preferably the wiki pages) to find the capital of the
country together with its population and area. The population has to be rounded and shown in
millions (without decimal point). The area should be rounded and shown in x10000 square 
kilometers (without decimal point).

When the program replies each user’s query, it’s its turn to ask the user to answer a similar query 
for a different country, and gives 15 seconds to the user to answer the query. If the user does not 
answer in 15 seconds, the game is over. If the user enters a country that has been already discussed 
(in any direction), the program should inform that the country was already discussed; and the game is over.

Similarly, the program should not ask any country that has been discussed. 
VERY Important note: you are not allowed to hardcode name of any country or its capital 
inside your code. 

The conversation should NOT be case sensitive. However, if there is misspelling, the user loses 
the game. Similarly, if the user asks for a country that does not exist in Wiki (or is misspelled), the use 
loses the game.

If the user does not lose the game before asking for 10 countries, he/she wins.
