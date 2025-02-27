#!/usr/bin/env python3

import math
import os
os.environ['PYGAME_HIDE_SUPPORT_PROMPT'] = "hide"
import pygame
import sys
import utils

# Definir colors
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
RED = (255, 0, 0)
GREEN = (0, 255, 0)
BLUE  = (0, 0, 255)
PURPLE = (128, 0, 128)
ORANGE = (255, 165, 0) 
pygame.init()
clock = pygame.time.Clock()

# Definir la finestra
screen = pygame.display.set_mode((640, 480))
pygame.display.set_caption('Window Title')
window_size = {
    "width": screen.get_width(),
    "height": screen.get_height(),
    "center": {
        "x": screen.get_width() // 2,
        "y": screen.get_height() // 2
    }
}
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
    for event in pygame.event.get():
        if event.type == pygame.QUIT: # Bot� tancar finestra
            return False
    return True

# Fer c�lculs
def app_run():
    global window_size
    window_size["width"] = screen.get_width()
    window_size["height"] = screen.get_height()
    window_size["center"]["x"] = int(screen.get_width() / 2)
    window_size["center"]["y"] = int(screen.get_height() / 2)

# Dibuixar
def app_draw():
    # Pintar el fons de blanc
    screen.fill(WHITE)
    global window_size

    # Dibuixar la graella de coordenades (llibreria utils)
    utils.draw_grid(pygame, screen, 50)
    for q in range(20, 0, -1):
            perspective = (q / 20)
            q_ample = q * 25 * perspective
            q_alt = q * 20 * perspective
            if q_alt % 2 == 0:
                color = BLUE
            else:
                color = GREEN
            pygame.draw.rect(screen, color, (window_size["center"]["x"] - q_ample // 2, window_size["center"]["y"] - q_alt // 2, q_ample, q_alt))


    # Actualitzar el dibuix a la finestra
    pygame.display.update()

if __name__ == "__main__":
    main()
