package gsim

/**
 * @author Richard Mihalovič
 */
class SchemaRunner {
	static void main(arguments) {

		println '=== SCHEMA RUNNER ==='

//		if(arguments.size() == 0) {
//			println 'USAGE: SchemaRunner Simulation.schema'
//			return
//		}

		def schema = new Schema()

		def parameters = [
		    startTime: 0.0,
			stopTime: 1.0,
			timeStep: 0.05,
			schema: schema
		]

		groovy.util.Eval.me(
			'simulation',
			parameters,
			new File("./src/main/groovy/gsim/examples/SinCos.schema").getText("UTF-8")
		)

		def solver = new gsim.solvers.SolverTargetToSource()

		solver.setSchema(schema)

		BigDecimal tStart = parameters.startTime
		BigDecimal tStop = parameters.stopTime
		BigDecimal tStep = parameters.timeStep
		BigDecimal t = 0.0
		Integer i = 0

		println 'tStart:' + tStart + ' tStop:' + tStop + ' tStep:' + tStep

		t = tStart
		while(t <= tStop) {
			solver.setSimulationParameters(
				new gsim.SimulationParameters(
					t,
					i,
					tStep
				)
			)
			solver.computeSimulationStep()

			t += tStep
			i++
		}

		def targets = solver.findTargetBlocks().groupBy { it.blockId }

		targets.each { k,v ->
			if(v.size() != 0)
				TargetBlockChart.display(v[0])
		}

		schema.exportToGraphViz('')
	}
}
