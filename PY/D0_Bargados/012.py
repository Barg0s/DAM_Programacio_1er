import math
import os
os.environ['PYGAME_HIDE_SUPPORT_PROMPT'] = "hide"
import pygame
import sys
import utils,random
import importlib.util
import sys

# Especifica la ruta al directorio que contiene el archivo emojis.py
file_path = r"Primero(repet)\Programacio\assets\svgmoji"

# Añadir el directorio al sys.path
sys.path.append(file_path)

# Ahora puedes importar el archivo como si fuera un módulo normal
import emojis


# Definir colors
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
GRAY = (200, 200, 200)
PINK = (255, 105, 180)
GREEN = (0, 255, 0)
YEP = (100, 150, 100)
LIGHT_BLUE = (173, 216, 230)
BLUE = (0, 0, 255)
RED = (255, 0, 0)

pygame.init()
clock = pygame.time.Clock()

# Definir la finestra
screen = pygame.display.set_mode((640, 480))
pygame.display.set_caption('Window Title')


CELL_SIZE = 50
pos_skater = { "row": 0, "column": 0}
img_tree = emojis.get_emoji(pygame, "🌲", size=CELL_SIZE)
img_sman = emojis.get_emoji(pygame, "☃️", size=CELL_SIZE)
img_snow = emojis.get_emoji(pygame, "❄️", size=CELL_SIZE)
img_skater = emojis.get_emoji(pygame, "🏂", size=CELL_SIZE)


board = []

def place_random_letters(letter, count):
    for _ in range(count):
        pos_fila = random.randint(0, 7)
        pos_col = random.randint(0, 9)
        board[pos_fila][pos_col] = letter

def init_board(board):
    for i in range(8):
        fila = []  
        for j in range(10):
            fila.append("")  
        board.append(fila)
    (place_random_letters("T",9))
    (place_random_letters("S",3))
    (place_random_letters("M",3))

def is_skiable_cell(row, col):
    global board

    is_within_bounds = 0 <= row < len(board) and 0 <= col < len(board[0])
    if not is_within_bounds:
        return False
    
    cell_content = board[row][col]
    return cell_content == '' or cell_content == 'S'



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

# Gestionar eventos
def app_events():
    for event in pygame.event.get():
        if event.type == pygame.QUIT:  # Botón para cerrar la ventana
            return False
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_LEFT:  # Movimiento hacia la izquierda
                # Comprobar si la celda a la izquierda es esquiable
                if pos_skater["column"] > 0 and is_skiable_cell(pos_skater["row"], pos_skater["column"] - 1):
                    pos_skater["column"] -= 1
            elif event.key == pygame.K_RIGHT:  # Movimiento hacia la derecha
                # Comprobar si la celda a la derecha es esquiable
                if pos_skater["column"] < 9 and is_skiable_cell(pos_skater["row"], pos_skater["column"] + 1):
                    pos_skater["column"] += 1
            elif event.key == pygame.K_DOWN:  # Movimiento hacia abajo
                # Comprobar si la celda hacia abajo es esquiable
                if pos_skater["row"] < 7 and is_skiable_cell(pos_skater["row"] + 1, pos_skater["column"]):
                    pos_skater["row"] += 1
            elif event.key == pygame.K_UP:  # Movimiento hacia arriba
                # Comprobar si la celda hacia arriba es esquiable
                if pos_skater["row"] > 0 and is_skiable_cell(pos_skater["row"] - 1, pos_skater["column"]):
                    pos_skater["row"] -= 1
    return True



# Fer c�lculs
def app_run():
    pass

# Dibuixar
init_board(board)
def app_draw():
    screen.fill(WHITE)
    utils.draw_grid(pygame, screen, 50)

    # Dibuixar el tauler
    start_x = 50
    start_y = 50
    for row in range(len(board)):
        for col in range(len(board[row])):
            x = start_x + col * CELL_SIZE
            y = start_y + row * CELL_SIZE
            pygame.draw.rect(screen, LIGHT_BLUE, (x, y, CELL_SIZE, CELL_SIZE))

            if board[row][col] != '':
                if board[row][col] == 'T':
                    screen.blit(img_tree, (x, y, CELL_SIZE, CELL_SIZE))
                elif board[row][col] == 'M':
                    screen.blit(img_sman, (x, y, CELL_SIZE, CELL_SIZE))
                elif board[row][col] == 'S':
                    screen.blit(img_snow, (x, y, CELL_SIZE, CELL_SIZE))

    # Dibuixar el personatge
    x = start_x + pos_skater["column"] * CELL_SIZE
    y = start_y + pos_skater["row"] * CELL_SIZE
    screen.blit(img_skater, (x, y, CELL_SIZE, CELL_SIZE))

    pygame.display.update()

if __name__ == "__main__":
    main()
