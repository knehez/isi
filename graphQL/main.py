import strawberry
from fastapi import FastAPI
from strawberry.fastapi import GraphQLRouter

# User adatmodell
@strawberry.type
class User:
    id: int
    name: str
    age: int

# Példa adatbázis
users = [
    User(id=1, name="Noa", age=30),
    User(id=2, name="Anna", age=25),
]

# GraphQL Query osztály
@strawberry.type
class Query:
    @strawberry.field
    def get_users(self) -> list[User]:
        return users

# GraphQL Mutáció osztály (új felhasználó hozzáadása)
@strawberry.type
class Mutation:
    @strawberry.mutation
    def create_user(self, name: str, age: int) -> User:
        new_user = User(id=len(users) + 1, name=name, age=age)
        users.append(new_user)
        return new_user

# GraphQL séma létrehozása
schema = strawberry.Schema(query=Query, mutation=Mutation)

# FastAPI alkalmazás létrehozása
app = FastAPI()

# GraphQL endpoint regisztrálása
graphql_app = GraphQLRouter(schema)
app.include_router(graphql_app, prefix="/graphql")

# Futtatás: uvicorn.exe main:app --reload
