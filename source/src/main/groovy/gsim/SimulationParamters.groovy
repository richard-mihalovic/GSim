package gsim

/**
 *
 * @author Richard Mihalovič
 */
//@Immutable
class SimulationParameters {
	final def Double simulationTime
	final def Double simulationStep
	final def Integer simulationIndex

	def SimulationParameters() {
		simulationTime  = 0.0
		simulationStep  = 0.01
		simulationIndex = 0
	}
	
	def SimulationParameters(time, index, delta) {
		simulationTime	= time
		simulationIndex = index
		simulationStep 	= delta
	}
}

