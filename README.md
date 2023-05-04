# RecipesHyperskillProject

This is a recipe management application that allows users to create, read, update, and delete recipes. The application is secured with basic authentication and authorization.

- [Hyperskill project](https://hyperskill.org/projects/180)
## Acknowledgements

Getting started Prerequisites To run this application, you will need:

- Java 8 or later
- Gradle
- By default, this app uses H2 database which is an embedded database. However, if you want to use a different database management system (DBMS) like MySQL, you will need to configure the app to use it instead of H2. This can involve modifying the app's configuration files to specify the database driver, URL, username, and password for connecting to the MySQL database
- All endpoints except POST /api/register are secured!
## Usage/Examples

#### Registering a user 
To register a new user, send a POST request to /api/register with a UserDto object in the request body:

```json
POST /api/register
{
  "email": "jdoe@example.com"
  "password": "password123",
}
```

#### Creating a recipe
To create a new recipe, send a POST request to /api/recipe/new with a RecipeDto object in the request body:

```json
POST /api/recipe/new
{
        "name": "Warming Ginger Tea",
        "category": "beverage",
        "description": "Ginger tea is a warming drink for cool weather, ...",
        "ingredients": [
            "1 inch ginger root, minced",
            "1/2 lemon, juiced",
            "1/2 teaspoon manuka honey"
        ],
        "directions": [
            "Place all ingredients in a mug and fill with warm water (not too hot so you keep the   beneficial honey compounds in tact)",
            "Steep for 5-10 minutes",
            "Drink and enjoy"
        ]
    }
```

#### Retrieving a recipe
To retrieve a recipe by ID, send a GET request to /api/recipe/{id}
```json
onl
GET /api/recipe/1
{
        "name": "Warming Ginger Tea",
        "category": "beverage",
        "date": "2023-05-02T19:13:57.961120",
        "description": "Ginger tea is a warming drink for cool weather, ...",
        "ingredients": [
            "1 inch ginger root, minced",
            "1/2 lemon, juiced",
            "1/2 teaspoon manuka honey"
        ],
        "directions": [
            "Place all ingredients in a mug and fill with warm water (not too hot so you keep the   beneficial honey compounds in tact)",
            "Steep for 5-10 minutes",
            "Drink and enjoy"
        ]
}
```

#### Updating a recipe
To update a recipe by ID, send a PUT request to /api/recipe/{id} with a RecipeDto object in the request body:
```json
Only the user who created the recipe can update it!
PUT /api/recipe/1
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": [
       "boiled water",
       "honey", 
       "fresh mint leaves"],
   "directions": [
       "Boil water",
       "Pour boiling hot water into a mug",
       "Add fresh mint leaves", 
       "Mix and let the mint leaves seep for 3-5 minutes",
       "Add honey and mix again"]
}
```

#### Deleting a recipe

```json
Only the user who created the recipe can delete it!
DELETE /api/recipe/{id}
```

#### Retrieving recipes by category/name
```json
GET /api/recipe/search?category={category}&name={name}
```
Returns a list of recipes matching the specified category and/or name.
