#!/usr/bin/env python3

import math
import os
os.environ['PYGAME_HIDE_SUPPORT_PROMPT'] = "hide"
import pygame
import sys
import utils,random

# Definir colors
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
GREEN = (127, 184, 68)
YELLOW = (240, 187, 64)
ORANGE = (226, 137, 50)
RED = (202, 73, 65)
PURPLE = (135, 65, 152)
BLUE  = (75, 154, 217)
colors = [GREEN, YELLOW, ORANGE, RED, PURPLE, BLUE]
pygame.init()
clock = pygame.time.Clock()
# Definir la finestra
screen = pygame.display.set_mode((640, 480))
pygame.display.set_caption('Window Title')
window_size = { 
    "width": 0, 
    "height": 0, 
    "center": {
        "x": 0,
        "y": 0
    } 
}
mouse_pos = { "x": -1, "y": -1 }
circles = []
# Bucle de l'aplicaci�
def main():
    is_looping = True

    while is_looping:
        is_looping = app_events()
        app_run()
        app_draw()

        clock.tick(60) # Limitar a 60 FPS

    # Fora del bucle, tancar l'aplicaci�
    pygame.quit()
    sys.exit()

# Gestionar events
def app_events():
    global mouse_pos
    mouse_inside = pygame.mouse.get_focused() # El ratolí està dins de la finestra?

    for event in pygame.event.get():
        if event.type == pygame.QUIT: # Botó tancar finestra
            return False
        elif event.type == pygame.MOUSEBUTTONDOWN:
            if mouse_inside:
                mouse_pos["x"] = event.pos[0]
                mouse_pos["y"] = event.pos[1]
                color = random.choice(colors)
                radi = 25
                circles.append([mouse_pos["x"], mouse_pos["y"], color,radi])
            else:
                mouse_pos["x"] = -1
                mouse_pos["y"] = -1
    return True
# Fer c�lculs
def app_run():

    delta_time = clock.get_time() / 1000.0

    speed = 5
    for circle in circles:
        if circle[3] > 5:
            circle[3] -= speed * delta_time
        else:
            circle[3] = 5


# Dibuixar
def app_draw():

    # Pintar el fons de blanc
    screen.fill(WHITE)

    # Dibuixar la graella de coordenades (llibreria utils)
    utils.draw_grid(pygame, screen, 50)
    for (x, y, color,radi) in circles:
        pygame.draw.circle(screen, color, (x, y), radi)

    # Actualitzar el dibuix a la finestra
    pygame.display.update()

if __name__ == "__main__":
    main()
