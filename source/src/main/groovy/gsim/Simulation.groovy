package gsim

import gsim.solvers.SchemaSolver

/**
 *
 * @author Richard Mihalovič
 */
class Simulation {
	def Schema schema
	def SchemaSolver solver

	def Integer index = 0
	def Double time = 0.0
	def Double delta = 0.01
	def Double startTime = 0
	def Double stopTime = 0

	def Simulation(schema, solver, delta=0.01, startTime=0, stopTime=0) {
		this.schema = schema
		this.solver = solver
		this.solver.schema = schema

		this.delta = delta
		this.startTime = startTime
		this.stopTime = stopTime
	}

	def step() {
		def simulationParameters = new SimulationParameters(time, index, delta)

		solver.simulationParameters = simulationParameters
		solver.computeSimulationStep()

		index++
		time += delta
	}

}

