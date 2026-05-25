import tkinter as tk
from tkinter import ttk
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

# Piękny import prosto z naszego nowego pakietu 'logika'
from logika import oblicz_moce, temperatura, wilgotnosc, co2, temp_zewnetrzna, wilgotnosc_zewnetrzna, cyrkulator, klima

class AplikacjaCyrkulatora:
    def __init__(self, root):
        self.root = root
        self.root.title("Panel Inżynierski HVAC - Pełna Analiza Systemu")
        self.root.geometry("1400x700") 
        self.root.resizable(False, False)

        style = ttk.Style()
        style.configure("TLabel", font=("Arial", 10))
        style.configure("Wynik.TLabel", font=("Arial", 16, "bold"), foreground="#0055ff")

        self.konfiguracja = [
            {"id": "t_wew", "nazwa": "Temp. wewnątrz [°C]", "zmienna": temperatura, "min": 15, "max": 35, "start": 22},
            {"id": "h_wew", "nazwa": "Wilgotność wew. [%]", "zmienna": wilgotnosc, "min": 20, "max": 80, "start": 50},
            {"id": "co2", "nazwa": "Stężenie CO2 [ppm]", "zmienna": co2, "min": 400, "max": 2000, "start": 800},
            {"id": "t_zew", "nazwa": "Temp. zewnątrz [°C]", "zmienna": temp_zewnetrzna, "min": -20, "max": 40, "start": 15},
            {"id": "h_zew", "nazwa": "Wilgotność zew. [%]", "zmienna": wilgotnosc_zewnetrzna, "min": 20, "max": 100, "start": 50},
        ]
        
        self.zmienne_gui = {}
        self.wyjscie_cyrkulator = {}
        self.wyjscie_klimatyzator = {}

        self.zbuduj_interfejs()
        self.przelicz_wynik_z_gui()

    def zbuduj_interfejs(self):
        frame_input = ttk.LabelFrame(self.root, text=" Moduł Diagnostyczny Czujników (Wejścia) ", padding=10)
        frame_input.pack(fill="x", padx=10, pady=5)

        for i in range(5):
            frame_input.columnconfigure(i, weight=1)

        for i, konf in enumerate(self.konfiguracja):
            col_frame = ttk.Frame(frame_input)
            col_frame.grid(row=0, column=i, padx=5, sticky="nsew")
            self.buduj_kolumne_wejsciowa(col_frame, konf)

        frame_bottom = ttk.Frame(self.root)
        frame_bottom.pack(fill="both", expand=True, padx=10, pady=5)

        frame_tryb = ttk.LabelFrame(frame_bottom, text=" Panel Użytkownika ", padding=15)
        frame_tryb.pack(side="left", fill="y", padx=(0, 5))

        self.var_tryb = tk.StringVar(value="Dzień")
        ttk.Radiobutton(frame_tryb, text="Tryb Dzienny", variable=self.var_tryb, value="Dzień", command=self.przelicz_wynik_z_gui).pack(anchor="w", pady=5)
        ttk.Radiobutton(frame_tryb, text="Tryb Nocny (Cichy)", variable=self.var_tryb, value="Noc", command=self.przelicz_wynik_z_gui).pack(anchor="w", pady=5)

        frame_output = ttk.LabelFrame(frame_bottom, text=" Stan Zespołu Napędowego (Wyjścia) ", padding=10)
        frame_output.pack(side="right", fill="both", expand=True, padx=(5, 0))
        
        frame_output.columnconfigure(0, weight=1)
        frame_output.columnconfigure(1, weight=1)

        frame_out_cyrk = ttk.Frame(frame_output)
        frame_out_cyrk.grid(row=0, column=0, sticky="nsew", padx=10)
        self.wyjscie_cyrkulator = self.buduj_wykres_wyjsciowy(frame_out_cyrk, "Moc Cyrkulatora (Wymiana Powietrza)", cyrkulator)

        frame_out_klima = ttk.Frame(frame_output)
        frame_out_klima.grid(row=0, column=1, sticky="nsew", padx=10)
        self.wyjscie_klimatyzator = self.buduj_wykres_wyjsciowy(frame_out_klima, "Moc Klimatyzatora (Chłodzenie)", klima)

    def buduj_kolumne_wejsciowa(self, parent, konf):
        ttk.Label(parent, text=konf["nazwa"], font=("Arial", 9, "bold")).pack(pady=(0, 5))
        zmienna_tk = tk.DoubleVar(value=konf["start"])
        self.zmienne_gui[konf["id"]] = zmienna_tk
        lbl_val = ttk.Label(parent, text=f"{konf['start']:.1f}")
        
        fig, ax = plt.subplots(figsize=(2.5, 2.0))
        plt.subplots_adjust(left=0.05, right=0.95, top=0.95, bottom=0.1)
        fig.patch.set_facecolor('#f0f0f0')

        for term_name, term in konf["zmienna"].terms.items():
            ax.plot(konf["zmienna"].universe, term.mf, label=term_name, linewidth=1.5)
            ax.fill_between(konf["zmienna"].universe, 0, term.mf, alpha=0.15)

        ax.set_yticks([]) 
        ax.set_ylim(0, 1.05)
        ax.margins(x=0)

        vline = ax.axvline(x=konf["start"], color='red', linewidth=2, linestyle='--')
        canvas = FigureCanvasTkAgg(fig, master=parent)
        canvas_widget = canvas.get_tk_widget()

        def on_slide(val):
            v = float(val)
            lbl_val.config(text=f"{v:.1f}")
            vline.set_xdata([v, v])
            canvas.draw_idle()
            self.przelicz_wynik_z_gui()

        scale = ttk.Scale(parent, from_=konf["min"], to=konf["max"], variable=zmienna_tk, command=on_slide)
        scale.pack(fill="x")
        lbl_val.pack(pady=2)
        canvas_widget.pack(fill="both", expand=True)

    def buduj_wykres_wyjsciowy(self, parent, tytul, zmienna_rozmyta):
        ttk.Label(parent, text=tytul, font=("Arial", 10, "bold")).pack(pady=(5, 0))
        lbl_wynik = ttk.Label(parent, text="0.0 %", style="Wynik.TLabel")
        lbl_wynik.pack(pady=2)

        fig, ax = plt.subplots(figsize=(4.0, 2.2))
        plt.subplots_adjust(left=0.05, right=0.95, top=0.95, bottom=0.15)
        fig.patch.set_facecolor('#e8f4f8') 

        for term_name, term in zmienna_rozmyta.terms.items():
            ax.plot(zmienna_rozmyta.universe, term.mf, label=term_name, linewidth=1.5)
            ax.fill_between(zmienna_rozmyta.universe, 0, term.mf, alpha=0.15)

        ax.set_yticks([])
        ax.set_ylim(0, 1.05)
        ax.margins(x=0)

        vline = ax.axvline(x=0, color='red', linewidth=3, linestyle='-')
        canvas = FigureCanvasTkAgg(fig, master=parent)
        canvas_widget = canvas.get_tk_widget()
        canvas_widget.pack(fill="both", expand=True)

        return {"lbl": lbl_wynik, "vline": vline, "canvas": canvas}

    def przelicz_wynik_z_gui(self):
        try:
            t_wew = self.zmienne_gui["t_wew"].get()
            h_wew = self.zmienne_gui["h_wew"].get()
            c_val = self.zmienne_gui["co2"].get()
            t_zew = self.zmienne_gui["t_zew"].get()
            h_zew = self.zmienne_gui["h_zew"].get()
            tryb = self.var_tryb.get()

            moc_cyrkulatora, moc_klimatyzacji = oblicz_moce(t_wew, h_wew, c_val, t_zew, h_zew, tryb)

            self.wyjscie_cyrkulator["lbl"].config(text=f"{moc_cyrkulatora:.1f} %")
            self.wyjscie_cyrkulator["vline"].set_xdata([moc_cyrkulatora, moc_cyrkulatora])
            self.wyjscie_cyrkulator["canvas"].draw_idle()

            self.wyjscie_klimatyzator["lbl"].config(text=f"{moc_klimatyzacji:.1f} %")
            self.wyjscie_klimatyzator["vline"].set_xdata([moc_klimatyzacji, moc_klimatyzacji])
            self.wyjscie_klimatyzator["canvas"].draw_idle()
            
        except Exception:
            self.wyjscie_cyrkulator["lbl"].config(text="Błąd")
            self.wyjscie_klimatyzator["lbl"].config(text="Błąd")