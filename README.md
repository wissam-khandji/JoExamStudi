# E-Tickets JO 2024 – API Backend

Ce projet est une application backend développée avec Spring Boot pour gérer la réservation et la distribution d'e-tickets pour les Jeux Olympiques 2024 en France.

L’API permet de :
- Consulter et créer des événements et des offres.
- Gérer les utilisateurs et les paniers.
- Simuler un paiement (via Stripe en mode test) et finaliser la génération d’un billet électronique sous forme de QR code.
- Accéder à un espace administrateur permettant de visualiser le nombre de ventes par offre.

## Prérequis

Avant de lancer l’API, assurez-vous d’avoir installé sur votre ordinateur :
- **Java 17** (ou une version compatible avec Spring Boot 3)
- **Maven**
- **Git** (pour cloner le dépôt)

## Installation et Lancement

1. **Cloner le dépôt :**

   Ouvrez un terminal et exécutez la commande suivante :

   ```bash
   git clone https://github.com/votre-utilisateur/votre-depot.git

2. ## Se placer dans le répertoire du projet :## 
    cd votre-depot


3. ## Compiler et lancer l’application avec Maven :##
    mvn spring-boot:run

   Cette commande télécharge les dépendances, compile le projet et démarre l’application sur le port 8080 par défaut.


4. ## Accéder à l’API : ##
      Événements :
      GET http://localhost:8080/api/events
   
      Offres :
      GET http://localhost:8080/api/offers
   
      Utilisateurs (restreint aux administrateurs) :
      GET http://localhost:8080/api/users
   
      Paniers :
      GET http://localhost:8080/api/carts
   
      Tickets :
      GET http://localhost:8080/api/tickets
   
      QR Code :
      GET http://localhost:8080/api/qrcode?text=HelloWorld
      (La réponse sera une image PNG.)
   
      Finalisation de paiement et génération du ticket :
      POST http://localhost:8080/api/payment/finalize?amount=5000&currency=eur&cartId=1


   # Authentification et Sécurité
   
    L’API utilise l’authentification HTTP Basic.
    
    Certains endpoints sensibles sont restreints :
    
    Les endpoints sous /api/users et /api/admin/** sont accessibles uniquement aux utilisateurs ayant le rôle ADMIN.
    
    Pour tester en local, utilisez par exemple :
    Administrateur :
    Email : admin@example.com
    Mot de passe : admin123
    
    Utilisateur régulier :
    Email : user@example.com
    Mot de passe : user123
