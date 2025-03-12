import math
import os
os.environ['PYGAME_HIDE_SUPPORT_PROMPT'] = "hide"
import pygame
import sys
import utils,random

# Definir colors
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
GRAY = (200, 200, 200)
PINK = (255, 105, 180)
GREEN = (0, 255, 0)
YEP = (100, 150, 100)

BLUE = (0, 0, 255)
RED = (255, 0, 0)

pygame.init()
clock = pygame.time.Clock()

# Definir la finestra
screen = pygame.display.set_mode((640, 480))
pygame.display.set_caption('Window Title')

# Bucle de l'aplicaci
def main():

    is_looping = True
    global window_height,window_width,coords
    window_width, window_height = screen.get_size()
    coords = []
    for i in range(10):
        x = random.randint(0, window_width)
        y = random.randint(0, window_height)
        coords.append((x, y))

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

    pass
# Dibuixar
def app_draw():
    screen.fill(WHITE)
    utils.draw_grid(pygame, screen, 50)
    pygame.draw.polygon(screen, BLACK, coords, 5)

    pygame.display.update()

if __name__ == "__main__":
    main()
