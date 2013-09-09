![GSim screenshot](https://raw.github.com/richard-mihalovic/GSim/master/screenshot.png)

Example schema code:

	simulation.startTime = 0.0
	simulation.stopTime = 2*Math.PI
	simulation.timeStep = 0.05

	simulation.schema.with {
		block('sin', 'source.sin', [])
		block('cos', 'source.sin', [phase: Math.PI/2])
		block('target12', 'target.xyscope', [])

		block('target1', 'target.scope', [])
		block('target2', 'target.scope', [])

		connect 'sin:O1', 'target12:I1'
		connect 'cos:O1', 'target12:I2'

		connect 'sin:O1', 'target1:I1'
		connect 'cos:O1', 'target2:I1'
	}
