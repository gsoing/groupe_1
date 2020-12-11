# TP 2 MRA

TP2 MRA, rendu de Aguesse Justin, Jacquet Pierre

## Installation

L'application peut être lancée via les commandes suivantes

```bash
gradlew build
docker-compose up --build
```

## Usage

Une collection Postman a été rajoutée au projet afin de pouvoir faciliter les tests
Le mécanisme de Lock positive utilise le HEADER eTag :
Il est renseigné dans le header retour d'un GET sur un document, dans le header retour d'un PUT sur un document. 
Il doit être renseigné dans header envoyé d'une requête PUT sur un document
Les heures sont en UTC.

## Authentification

L'application utilise le mécanisme Basic Auth avec deux comptes :
- Compte REDACTEUR : username=redacteur, password=redacteur 
- Compte RELECTEUR : username=relecteur, password=relecteur 