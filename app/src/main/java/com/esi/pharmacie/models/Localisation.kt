package com.esi.pharmacie.models

import java.io.Serializable

class Localisation  ( val type : String = "Point" , val coordinates : Array<Double> = Array(2) {0.0;0.0}) : Serializable