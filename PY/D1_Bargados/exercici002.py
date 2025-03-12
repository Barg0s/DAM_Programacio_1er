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

def draw_text(text, font, x, y, align_x="left", align_y="top"):
    text_surface = font.render(text, True, (0, 0, 0))
    text_rect = text_surface.get_rect()
    if align_x == "center":
        text_rect.centerx = x
    elif align_x == "right":
        text_rect.right = x
    elif align_x == "left":
        text_rect.left = x
    else:  
        text_rect.x = x

    if align_y == "center":
        text_rect.centery = y
    elif align_y == "bottom":
        text_rect.bottom = y
    else: 
        text_rect.y = y

    screen.blit(text_surface, text_rect)


# Fer c�lculs
def app_run():
    pass

# Dibuixar
def app_draw():
    # Pintar el fons de blanc
    screen.fill(WHITE)

    # Dibuixar la graella de coordenades (llibreria utils)
    utils.draw_grid(pygame, screen, 50)
    fontArial = pygame.font.SysFont("Arial", 15)

    pygame.draw.circle(screen, BLUE, (100, 50), 5)
    draw_text("Poma", fontArial, 100, 50, "left", "bottom")

    pygame.draw.circle(screen, BLUE, (100, 100), 5)
    draw_text("Pera", fontArial, 100, 100, "center", "center")

    pygame.draw.circle(screen, BLUE, (100, 150), 5)
    draw_text("Raïm", fontArial, 100, 150, "right", "top")

    pygame.draw.circle(screen, BLUE, (250, 50), 5)
    draw_text("Plàtan", fontArial, 250, 50, "left", "top")

    pygame.draw.circle(screen, BLUE, (250, 100), 5)
    draw_text("Préssec", fontArial, 250, 100, "center", "center")
    
    pygame.draw.circle(screen, BLUE, (250, 150), 5)
    draw_text("Maduixa", fontArial, 250, 150, "right", "bottom")
    # Actualitzar el dibuix a la finestra
    pygame.display.update()

if __name__ == "__main__":
    main()
