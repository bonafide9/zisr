from skfuzzy import control as ctrl
from .zmienne import temperatura, wilgotnosc, co2, temp_zewnetrzna, wilgotnosc_zewnetrzna, cyrkulator, klima

reguly_dzien = [
    ctrl.Rule(temperatura['goraco'] & temp_zewnetrzna['zimno'] & (wilgotnosc['sucho'] | wilgotnosc['norma']) & (wilgotnosc_zewnetrzna['sucho'] | wilgotnosc_zewnetrzna['norma']), [klima['stop'], cyrkulator['max']]),
    ctrl.Rule(temperatura['goraco'] & temp_zewnetrzna['zimno'] & (wilgotnosc['sucho'] | wilgotnosc['norma']) & wilgotnosc_zewnetrzna['wilgotno'], [klima['stop'], cyrkulator['srednio']]),
    ctrl.Rule(temperatura['goraco'] & ((temp_zewnetrzna['optymalnie'] | temp_zewnetrzna['goraco']) | wilgotnosc['wilgotno']), [klima['max'], cyrkulator['max']]),
    ctrl.Rule((temperatura['optymalnie'] | temperatura['zimno']) & wilgotnosc['wilgotno'], [klima['srednio'], cyrkulator['max']]),
    ctrl.Rule((temperatura['optymalnie'] | temperatura['zimno']) & (co2['wysokie'] | co2['srednie']) & temp_zewnetrzna['zimno'] & (wilgotnosc['sucho'] | wilgotnosc['norma']), [klima['stop'], cyrkulator['srednio']]),
    ctrl.Rule((temperatura['optymalnie'] | temperatura['zimno']) & (co2['wysokie'] | co2['srednie']) & (temp_zewnetrzna['optymalnie'] | temp_zewnetrzna['goraco']) & (wilgotnosc['sucho'] | wilgotnosc['norma']) & (wilgotnosc_zewnetrzna['sucho'] | wilgotnosc_zewnetrzna['norma']), [klima['stop'], cyrkulator['max']]),
    ctrl.Rule((temperatura['optymalnie'] | temperatura['zimno']) & (co2['wysokie'] | co2['srednie']) & (temp_zewnetrzna['optymalnie'] | temp_zewnetrzna['goraco']) & (wilgotnosc['sucho'] | wilgotnosc['norma']) & wilgotnosc_zewnetrzna['wilgotno'], [klima['stop'], cyrkulator['srednio']]),
    ctrl.Rule((temperatura['optymalnie'] | temperatura['zimno']) & co2['niskie'] & (wilgotnosc['sucho'] | wilgotnosc['norma']), [klima['stop'], cyrkulator['stop']])
]

reguly_noc = [
    ctrl.Rule(temperatura['goraco'] & temp_zewnetrzna['zimno'] & (wilgotnosc['sucho'] | wilgotnosc['norma']) & (wilgotnosc_zewnetrzna['sucho'] | wilgotnosc_zewnetrzna['norma']), [klima['stop'], cyrkulator['srednio']]),
    ctrl.Rule(temperatura['goraco'] & temp_zewnetrzna['zimno'] & (wilgotnosc['sucho'] | wilgotnosc['norma']) & wilgotnosc_zewnetrzna['wilgotno'], [klima['stop'], cyrkulator['stop']]),
    ctrl.Rule(temperatura['goraco'] & ((temp_zewnetrzna['optymalnie'] | temp_zewnetrzna['goraco']) | wilgotnosc['wilgotno']), [klima['srednio'], cyrkulator['srednio']]),
    ctrl.Rule((temperatura['optymalnie'] | temperatura['zimno']) & wilgotnosc['wilgotno'], [klima['stop'], cyrkulator['srednio']]),
    ctrl.Rule((temperatura['optymalnie'] | temperatura['zimno']) & (co2['wysokie'] | co2['srednie']) & temp_zewnetrzna['zimno'] & (wilgotnosc['sucho'] | wilgotnosc['norma']), [klima['stop'], cyrkulator['srednio']]),
    ctrl.Rule((temperatura['optymalnie'] | temperatura['zimno']) & (co2['wysokie'] | co2['srednie']) & (temp_zewnetrzna['optymalnie'] | temp_zewnetrzna['goraco']) & (wilgotnosc['sucho'] | wilgotnosc['norma']) & (wilgotnosc_zewnetrzna['sucho'] | wilgotnosc_zewnetrzna['norma']), [klima['stop'], cyrkulator['srednio']]),
    ctrl.Rule((temperatura['optymalnie'] | temperatura['zimno']) & (co2['wysokie'] | co2['srednie']) & (temp_zewnetrzna['optymalnie'] | temp_zewnetrzna['goraco']) & (wilgotnosc['sucho'] | wilgotnosc['norma']) & wilgotnosc_zewnetrzna['wilgotno'], [klima['stop'], cyrkulator['stop']]),
    ctrl.Rule((temperatura['optymalnie'] | temperatura['zimno']) & co2['niskie'] & (wilgotnosc['sucho'] | wilgotnosc['norma']), [klima['stop'], cyrkulator['stop']])
]