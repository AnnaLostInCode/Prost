# Post
- Backend for a drinking game heavily inspired by @Gnerfedurf.
- Special about it drinking game is the function to consent to certain tags like the tag "drinking". This allows players to only do/get the tasks they are comfortable with. 
- Additionally you can decide what tags your game contains and therefore control the vibe of your game.
- It also contains a leveling system. Which can be adjusted over the course of your game.

## Model
![Prost](https://github.com/AnnaLostInCode/Prost/assets/66720765/b29ca48b-3c3d-41e0-b920-0e265a41408b)

## Data Examples
**Game**
```json
{
  "id": "66042bc0982d75434cc4f75d",
  "tagIds": [
    "66042875982d75434cc4f755",
    "6604287e982d75434cc4f756",
    "6604288f982d75434cc4f757",
    "660428a0982d75434cc4f758"
  ],
  "playerIds": [
    "660428e0982d75434cc4f75a",
    "66042912982d75434cc4f75b",
    "66042923982d75434cc4f75c"
  ],
  "level": 0
}
```

**Player**
```json
{
  "id": "660428e0982d75434cc4f75a",
  "name": "Anna",
  "color": "Purple",
  "pronouns": "They/Them",
  "tagIds": [
    "66042875982d75434cc4f755",
    "660428a0982d75434cc4f758",
    "6604288f982d75434cc4f757",
    "660428ac982d75434cc4f759"
  ]
}
```

**Task**
```json
{
  "id": "66042caf982d75434cc4f75f",
  "level": 0,
  "amountPlayers": 2,
  "challenge": "whenever {1} drinks, {2} drinks as well",
  "tagIds": [
    "66042875982d75434cc4f755"
  ]
}
```

**Tag**
```json
{
  "id": "66042875982d75434cc4f755",
  "tag": "Drinking",
  "description": "Player is okay with drinking"
}
```

**ActiveTask**
```json
{
  "task": {
    "id": "66042d91982d75434cc4f763",
    "level": 0,
    "amountPlayers": 1,
    "challenge": "{1} can distribute as many drinks as they can do pull ups",
    "tagIds": [
      "6604288f982d75434cc4f757"
    ]
  },
  "involvedPlayers": [
    {
      "id": "66042912982d75434cc4f75b",
      "name": "Mättu",
      "color": "Green",
      "pronouns": "He/Him",
      "tagIds": [
        "66042875982d75434cc4f755",
        "660428a0982d75434cc4f758",
        "6604288f982d75434cc4f757",
        "660428ac982d75434cc4f759",
        "6604287e982d75434cc4f756"
      ]
    }
  ]
}
```

## App Specifications
- Java Spring Boot
- Maven
- Mongo DB
- Swagger UI
