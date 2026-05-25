import tkinter as tk
from interfejs.okno_glowne import AplikacjaCyrkulatora

def uruchom_program():
    okno_glowne = tk.Tk()
    app = AplikacjaCyrkulatora(okno_glowne)
    okno_glowne.mainloop()

if __name__ == "__main__":
    uruchom_program()