import math
import os
os.environ['PYGAME_HIDE_SUPPORT_PROMPT'] = "hide"
import pygame
import sys
import utils

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
colors = [(127, 184, 68), (240, 187, 64), (226, 137, 50), (202, 73, 65), (135, 65, 152), (75, 154, 217)]
board = [
    [0, 1, 2, 3, 4, 5, 4, 3],
    [1, 2, 3, 4, 5, 4, 3, 2],
    [2, 3, 4, 5, 4, 3, 2, 1],
    [3, 4, 5, 4, 3, 2, 1, 0],
    [4, 5, 4, 3, 2, 1, 0, 1],
    [5, 4, 3, 2, 1, 0, 1, 2],
    [4, 3, 2, 1, 0, 1, 2, 3],
    [3, 2, 1, 0, 1, 2, 3, 4],
]
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
    pass

# Dibuixar
def app_draw():
    screen.fill(WHITE)
    utils.draw_grid(pygame, screen, 50)
    for row in range(len(board)):
        for column in range(len(board)):
            num = board[row][column]
            x = 50 + column * 50
            y = 50 + row * 50
            rect = (x, y, 50, 50)
            pygame.draw.rect(screen, colors[num], rect)
    pygame.display.update()

if __name__ == "__main__":
    main()
