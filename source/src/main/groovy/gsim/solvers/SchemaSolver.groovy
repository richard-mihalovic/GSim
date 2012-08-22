package gsim.solvers

import gsim.Schema
import gsim.SimulationParameters

/**
 * @author Richard Mihalovič
 */
abstract class SchemaSolver {
    def Schema schema
    def SimulationParameters simulationParameters

    abstract def computeSimulationStep()
}
