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

# Bucle de l'aplicació
def main():
    is_looping = True

    while is_looping:
        is_looping = app_events()
        app_run()
        app_draw()

        clock.tick(60) # Limitar a 60 FPS

    # Fora del bucle, tancar l'aplicació
    pygame.quit()
    sys.exit()

# Gestionar events
def app_events():
    for event in pygame.event.get():
        if event.type == pygame.QUIT: # Botó tancar finestra
            return False
    return True

# Fer càlculs
def app_run():
    pass

# Dibuixar
def app_draw():
    screen.fill(WHITE)
    utils.draw_grid(pygame, screen, 50)
    pygame.draw.rect(screen,RED,pygame.Rect(50,50,550,100))
    font = pygame.font.SysFont("Courier New", 40, bold = True)
    text = font.render('World goes Wrong!', True, BLACK)
    screen.blit(text, (50, 160))

    fontT = pygame.font.SysFont("Arial", 60)
    text1 = fontT.render('HEADLINE NEWS', True, WHITE)
    screen.blit(text1, (75, 70))

    textyep = font .render('YEP#', True, YEP)
    screen.blit(textyep, (510,160))
    fontlorem = pygame.font.SysFont("Arial", 28)
    texta = fontlorem.render("Lorem ipsum dolor sit amet, consectetur", True, BLACK)
    textb = fontlorem.render("adipiscing elit, sed do eiusmod tempor", True, BLACK)
    textc = fontlorem.render("incididunt ut labore et dolore magna aliqua.", True, BLACK)
    screen.blit(texta, (50,250))
    screen.blit(textb, (50, 285))
    screen.blit(textc, (50, 320))
    pygame.display.update()

if __name__ == "__main__":
    main()