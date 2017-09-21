package uk.camsw.bloom

sealed trait Contains

case object Possibly extends Contains

case object No extends Contains