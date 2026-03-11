# Proyecto Integrador 1
**Autores:**
- Alejandro Vargas Ocampo
- Yuliana Corrales Castaño
- María Camila Berrío Pavas

---

## Descripción del proyecto

**Comercios Conecta** es una solución tecnológica diseñada para **pequeños y medianos comercios locales** que buscan digitalizar y optimizar sus procesos de gestión.

El sistema se compone de tres grandes bloques:

1. **Backend**: desarrollado en **Java con Spring Boot**, encargado de la lógica de negocio y la exposición de servicios RESTful.
2. **Base de datos**: gestionada en **PostgreSQL dentro de un contenedor Docker**, que asegura persistencia, escalabilidad y fácil despliegue.
3. **Frontend**: construido con **Next.js**, ofreciendo una interfaz moderna, rápida y adaptable que permite a los usuarios interactuar de manera sencilla con la aplicación.

---

## Objetivos principales

- Facilitar la **gestión de inventarios** y productos.
- Permitir el **registro y consulta de ventas** en tiempo real.
- Administrar usuarios y roles de manera segura.
- Proveer una interfaz amigable que conecte a los comercios con sus clientes.
- Implementar una arquitectura **modular y distribuida** que permita escalar cada componente de manera independiente.

---

## Tecnologías utilizadas

- **Backend:** Java 17, Spring Boot, Maven
- **Base de datos:** PostgreSQL en Docker, pgAdmin
- **Frontend:** Next.js, TypeScript, Tailwind CSS


---


##  Estado del proyecto

Actualmente en fase inicial de desarrollo. Se han definido:
- Entidades principales (`Usuario`, `Producto`, `Venta`).
- Configuración de la base de datos en Docker.
- Primer prototipo de conexión **frontend ↔ backend ↔ base de datos**.  