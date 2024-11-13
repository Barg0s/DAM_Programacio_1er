import math
import os
os.environ['PYGAME_HIDE_SUPPORT_PROMPT'] = "hide"
import pygame
import sys
import utils

# Definir colores
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


pos_x = 100  
dir_x = 'none'
size = 25
# Definir la ventana
screen = pygame.display.set_mode((640, 480))
limits = screen.get_width()
pygame.display.set_caption('Window Title')

def main():
    global pos_x, dir_x 

    is_looping = True

    while is_looping:
        is_looping = app_events()  
        app_run()  
        app_draw()  

        clock.tick(60)  
    pygame.quit()
    sys.exit()

# Gestionar eventos
def app_events():
    global dir_x

    for event in pygame.event.get():
        if event.type == pygame.QUIT: 
            return False
        elif event.type == pygame.KEYDOWN:  
            if event.key == pygame.K_LEFT:
                dir_x = 'left'
            elif event.key == pygame.K_RIGHT:
                dir_x = 'right'
        elif event.type == pygame.KEYUP:  
            if event.key == pygame.K_LEFT or event.key == pygame.K_RIGHT:
                dir_x = 'none'

    return True


def app_run():
    global dir_x, pos_x, size

    delta_time = clock.get_time() / 1000.0  # Convertir a segons
    
    speed = 100  # píxels per segon
    displacement = speed * delta_time

    limit_left = size
    limit_right = screen.get_width() - size

    if (dir_x == "right"):
        pos_x = pos_x + displacement
        if (pos_x > limit_right):
            pos_x = limit_right
    elif (dir_x == "left"):
        pos_x = pos_x - displacement
        if (pos_x < limit_left):
            pos_x = limit_left

    size = 10 + (pos_x / 8)
# Dibujar en pantalla
def app_draw():
    global pos_x
    screen.fill(WHITE) 
    utils.draw_grid(pygame, screen, 50)  


    font = pygame.font.SysFont("Arial", 24)
    text = font.render('Apreta les tecles (left/right)', True, BLACK)
    screen.blit(text, (50, 50))    
    pygame.draw.circle(screen, BLACK, (int(pos_x), 250), int(size))

    pygame.display.update()

if __name__ == "__main__":
    main()
