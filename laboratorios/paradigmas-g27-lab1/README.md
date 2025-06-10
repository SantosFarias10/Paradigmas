---
Titulo: Lab de Programación Funcional
Autores Grupo 27: Santino Ponchiardi, Luca Irrazabal, 
        Santos Farias, Brandon Michel
---
 
# Un lenguaje que vale más que mil dibujos #

Este lab propone la implementación de un lenguaje pequeño específico para una
tarea muy concreta: combinar dibujos básicos para crear diseños más
interesantes. A este tipo de lenguajes se los suele conocer como DSL (Domain
Specific Language: lenguaje de dominio específico) porque están pensados para
eso: proveer abstracciones adecuadas para resolver problemas acotados a cierto
ámbito. La idea original del lenguaje está en este
[artículo](https://cs.famaf.unc.edu.ar/~mpagano/henderson-funcgeo2.pdf "en
inglés") de Peter Henderson, que recomendamos leer.

## Experiencia ##

Tuvimos dificultades para concretar el laboratorio sobre los ultimos dias 
porque no lograbamos ver el dibujo con gloss, hasta que nos iluminamos y 
nos dimos cuenta que en una de las formuas del Interp.hs habiamos puesto 
mal un signo.

Dejando de lado este problema menor, el resto del laboratorio se nos resulto
agradable y divertido una vez podiamos ver lo que dibujaba en Gloss.

Cabe aclarar que tuvimos un participante que tuvo problemas de salud, por 
lo que no pudo participar en los commits. De todas formas en todo momento 
estuvo ayudando en lo posible en llamadas grupales.

## Bibliotecas Usadas ##

* Gloss, es una libreria popular para hacer gráficos en Haskell con una 
  interfaz clara, y muchos ejemplos simpáticos.

* GLUT, es un kit de herramientas para programas OpenGL que se usa para 
  crear interfaces de usuario

