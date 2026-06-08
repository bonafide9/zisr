from skfuzzy import control as ctrl
from .reguly import reguly_dzien, reguly_noc

system_dzien = ctrl.ControlSystem(reguly_dzien)
system_noc = ctrl.ControlSystem(reguly_noc)

def oblicz_moce(temp_wew, hum_wew, co2_val, temp_zew, hum_zew, tryb):
    if tryb == "Dzień":
        system = system_dzien
    elif tryb == "Noc":
        system = system_noc
    else:
        raise ValueError(f"Nieznany tryb symulacji: {tryb}")
    symulacja = ctrl.ControlSystemSimulation(system)
    
    symulacja.input['temperatura'] = temp_wew
    symulacja.input['wilgotnosc'] = hum_wew
    symulacja.input['co2'] = co2_val
    symulacja.input['temp_zewnetrzna'] = temp_zew
    symulacja.input['wilgotnosc_zewnetrzna'] = hum_zew
    
    symulacja.compute()
    return symulacja.output['cyrkulator'], symulacja.output['klima']