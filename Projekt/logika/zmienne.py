import numpy as np
import skfuzzy as fuzz
from skfuzzy import control as ctrl

# --- Inicjalizacja Zmiennych Wejściowych ---
temperatura = ctrl.Antecedent(np.arange(15, 36, 1), 'temperatura')
wilgotnosc = ctrl.Antecedent(np.arange(20, 81, 1), 'wilgotnosc')
co2 = ctrl.Antecedent(np.arange(400, 2001, 1), 'co2')
temp_zewnetrzna = ctrl.Antecedent(np.arange(-20, 41, 1), 'temp_zewnetrzna')
wilgotnosc_zewnetrzna = ctrl.Antecedent(np.arange(20, 101, 1), 'wilgotnosc_zewnetrzna')

# --- Inicjalizacja Zmiennych Wyjściowych ---
cyrkulator = ctrl.Consequent(np.arange(0, 101, 1), 'cyrkulator')
klima = ctrl.Consequent(np.arange(0, 101, 1), 'klima')

# --- Zbiory rozmyte ---
temperatura['zimno'] = fuzz.trapmf(temperatura.universe, [15, 15, 18, 21])
temperatura['optymalnie'] = fuzz.trimf(temperatura.universe, [19, 22, 26])
temperatura['goraco'] = fuzz.trapmf(temperatura.universe, [24, 28, 35, 35])

wilgotnosc['sucho'] = fuzz.trapmf(wilgotnosc.universe, [20, 20, 35, 45])
wilgotnosc['norma'] = fuzz.trimf(wilgotnosc.universe, [35, 50, 65])
wilgotnosc['wilgotno'] = fuzz.trapmf(wilgotnosc.universe, [55, 65, 80, 80])

co2['niskie'] = fuzz.trapmf(co2.universe, [400, 400, 600, 800])
co2['srednie'] = fuzz.trimf(co2.universe, [600, 900, 1200])
co2['wysokie'] = fuzz.trapmf(co2.universe, [1000, 1500, 2000, 2000])

temp_zewnetrzna['zimno'] = fuzz.trapmf(temp_zewnetrzna.universe, [-20, -20, -5, 16])
temp_zewnetrzna['optymalnie'] = fuzz.trimf(temp_zewnetrzna.universe, [12, 20, 26])
temp_zewnetrzna['goraco'] = fuzz.trapmf(temp_zewnetrzna.universe, [22, 30, 40, 40])

wilgotnosc_zewnetrzna['sucho'] = fuzz.trapmf(wilgotnosc_zewnetrzna.universe, [20, 20, 40, 55])
wilgotnosc_zewnetrzna['norma'] = fuzz.trimf(wilgotnosc_zewnetrzna.universe, [45, 60, 75])
wilgotnosc_zewnetrzna['wilgotno'] = fuzz.trapmf(wilgotnosc_zewnetrzna.universe, [65, 80, 100, 100])

for urzadzenie in [cyrkulator, klima]:
    urzadzenie['stop'] = fuzz.trimf(urzadzenie.universe, [0, 0, 40])
    urzadzenie['srednio'] = fuzz.trimf(urzadzenie.universe, [20, 50, 80])
    urzadzenie['max'] = fuzz.trimf(urzadzenie.universe, [60, 100, 100])