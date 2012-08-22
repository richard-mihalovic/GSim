//package gsim.solvers
//
//import org.junit.Test
//import static org.junit.Assert.*
//import gsim.blocks.mixed.math.Multiply
//
//class SolverTargetToSourceTest {
//	@Test
//	void simulateSimpleSchema() {
//		// SCHEMA: [source] -> [gain1] -> [gain2] -> [target]
//
//		def schema = new gsim.Schema()
//		schema.with {
//			block('source', 'source.constant', [value: 1.0])
//			block('gain1', 'mixed.gain', [scaleFactor: 10.0])
//			block('gain2', 'mixed.gain', [scaleFactor: 100.0])
//			block('target', 'target.logger', [ ])
//
//			connect 'source:O1', 'gain1:I1'
//			connect 'gain1:O1', 'gain2:I1'
//			connect 'gain2:O1', 'target:I1'
//		}
//
//        def solver = new gsim.solvers.SolverTargetToSource()
//        solver.setSchema(schema)
//        solver.setSimulationParameters(new gsim.SimulationParameters())
//        solver.computeSimulationStep()
//
//		assertEquals(
//			1000.0,
//			(Double) schema.findBlock('target').getInput(1)[0],
//			0.00001
//		)
//	}
//
//	@Test
//	void simulateSimpleSchemaAlternativeSyntax() {
//		def schema = new gsim.Schema()
//		schema.with {
//			block('source', 'source.constant', [value: 1.0])
//			block('gain1', 'mixed.gain', [scaleFactor: 10.0])
//			block('gain2', 'mixed.gain', [scaleFactor: 100.0])
//			block('target', 'target.logger', [ ])
//
//			connect 'source', 'O1', 'gain1', 'I1'
//			connect 'gain1', 'O1', 'gain2', 'I1'
//			connect 'gain2', 'O1', 'target', 'I1'
//		}
//
//        def solver = new gsim.solvers.SolverTargetToSource()
//        solver.setSchema(schema)
//        solver.setSimulationParameters(new gsim.SimulationParameters())
//        solver.computeSimulationStep()
//
//		assertEquals(
//			1000.0,
//			(Double) schema.findBlock('target').getInput(1)[0],
//			0.00001
//		)
//	}
//
//	@Test
//	void simulateSimpleSchema1() {
//		// SCHEMA:
//		//
//		// [source1] \
//		//             [multiply] -> [target]
//		// [source2] /
//
//		def source1 = new gsim.blocks.source.Constant(blockId: 'source1', value: 2.0)
//		def source2 = new gsim.blocks.source.Constant(blockId: 'source2', value: 3.0)
//		def multiply = new Multiply(blockId: 'multiply')
//		def target = new gsim.blocks.target.Logger(blockId: 'target')
//
//		def schema = new gsim.Schema()
//		schema.with {
//			addBlock(source1)
//			addBlock(source2)
//			addBlock(multiply)
//			addBlock(target)
//
//			addConnection('source1:O1', 'multiply:I1')
//			addConnection('source2:O1', 'multiply:I2')
//			addConnection('multiply:O1', 'target:I1')
//		}
//
//        def solver = new gsim.solvers.SolverTargetToSource()
//        solver.setSchema(schema)
//        solver.setSimulationParameters(new gsim.SimulationParameters())
//        solver.computeSimulationStep()
//
//		assertEquals(
//			6.0,
//			(Double) schema.findBlock('target').getInput(1)[0],
//			0.00001
//		)
//	}
//
//	@Test
//	void simulateSchemaWithVectorOutput() {
//		// SCHEMA: [sinus] -> [gain] -> [target]
//
//		def a = 1.0
//		def f = 1.0
//		def p = 0.0
//		def b = 0.0
//
//		def schema = new gsim.Schema()
//		schema.with {
//			block('source', 'source.sin', [
//			    amplitude: a,
//				frequency: f,
//				phase: p,
//				bias: b
//			])
//			block('gain', 'mixed.gain', [ scaleFactor: 5.0 ])
//			block('target', 'target.logger', [ ])
//
//			connect 'source:O1', 'gain:I1'
//			connect 'gain:O1', 'target:I1'
//		}
//
//        def solver = new gsim.solvers.SolverTargetToSource()
//
//		def pi = Math.PI
//		def sp1 = new gsim.SimulationParameters(0	, 0, 0.01)
//		def sp2 = new gsim.SimulationParameters(pi/4, 1, 0.01)
//		def sp3 = new gsim.SimulationParameters(pi/2, 2, 0.01)
//		def sp4 = new gsim.SimulationParameters(pi	, 3, 0.01)
//
//        solver.setSchema(schema)
//
//		// step1
//        solver.setSimulationParameters(sp1)
//        solver.computeSimulationStep()
//
//		assertEquals(
//			5.0 * a * Math.sin(f * 0 + p) + b,
//			(Double) schema.findBlock('target').getInput(1)[0],
//			0.00001
//		)
//
//		// step2
//        solver.setSimulationParameters(sp2)
//        solver.computeSimulationStep()
//
//		assertEquals(
//			5.0 * a * Math.sin(f * pi/4 + p) + b,
//			(Double) schema.findBlock('target').getInput(1)[1],
//			0.00001
//		)
//
//		// step3
//        solver.setSimulationParameters(sp3)
//        solver.computeSimulationStep()
//
//		assertEquals(
//			5.0 * a * Math.sin(f * pi/2 + p) + b,
//			(Double) schema.findBlock('target').getInput(1)[2],
//			0.00001
//		)
//
//		// step4
//        solver.setSimulationParameters(sp4)
//        solver.computeSimulationStep()
//
//		assertEquals(
//			5.0 * a * Math.sin(f * pi + p) + b,
//			(Double) schema.findBlock('target').getInput(1)[3],
//			0.00001
//		)
//
//		assertEquals(4, schema.findBlock('target').getInput(1).size())
//	}
//}
