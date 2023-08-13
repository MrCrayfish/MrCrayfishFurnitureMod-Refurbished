package com.mrcrayfish.furniture.refurbished.blockentity;

/**
 * Author: MrCrayfish
 */
public interface IProcessingBlock
{
    /**
     * Gets the entity mode for this processing block. See {@link ProcessingContainerBlockEntity.EnergyMode} for details
     */
    default EnergyMode getEnergyMode()
    {
        return EnergyMode.ONLY_WHEN_PROCESSING;
    }

    /**
     * @return The current energy available for this processing block
     */
    int getEnergy();

    /**
     * Adds the given amount to the current energy
     * @param energy the energy amount to add, or negative to subtract
     */
    void addEnergy(int energy);

    /**
     * Determines if this processing block requires energy. By default, a processing block
     * assumes that if there are any energy slots, it requires energy.
     * @return True if requires energy
     */
    boolean requiresEnergy();

    /**
     * Attempts to consume an item from an energy slot. If an item in the energy slot does not
     * provide any energy, it will be ignored. This method also has the option to simulate, which
     * means that it can be used to check if an item in the energy slots provides energy without
     * shrinking it.
     *
     * @param simulate set to true to check if energy can be provided
     * @return the amount of energy returned from consuming an energy item
     */
    int retrieveEnergy(boolean simulate);

    /**
     * Updates and gets the total processing time required to complete a process
     * @return The current or updated total processing time
     */
    int updateAndGetTotalProcessingTime();

    /**
     * @return The current total processing time
     */
    int getTotalProcessingTime();

    /**
     * @return The current time of the processing cycle
     */
    int getProcessingTime();

    /**
     * Sets the time of the processing cycle
     */
    void setProcessingTime(int time);

    /**
     * A callback method when the processing cycle is complete. This can be used to provide logic to
     * create an output, such as converting an item to a "result" item. See {@link ProcessingContainerBlockEntity}
     * for an example.
     */
    void onCompleteProcess();

    /**
     * @return True if the process cycle can start or continue to process
     */
    boolean canProcess();

    /**
     * The default implementation of the process cycle.
     */
    default void processTick()
    {
        boolean processing = false;
        if(this.canProcess())
        {
            // If energy is required, and no energy is left, attempt to add more energy
            if(this.requiresEnergy() && this.getEnergy() <= 0)
            {
                if(this.retrieveEnergy(true) > 0)
                {
                    this.addEnergy(this.retrieveEnergy(false));
                }
            }

            if(!this.requiresEnergy() || this.getEnergy() > 0)
            {
                processing = true;

                // Get the time required to complete a process
                int totalProcessingTime = this.updateAndGetTotalProcessingTime();

                // Increase the process time if not yet reach the final process time and consume energy
                int processingTime = this.getProcessingTime();
                if(processingTime < totalProcessingTime)
                {
                    this.setProcessingTime(processingTime + 1);
                    if(this.requiresEnergy() && this.getEnergyMode() == EnergyMode.ONLY_WHEN_PROCESSING)
                    {
                        this.addEnergy(-1);
                    }
                }

                // Finally check if the process time is finished and output the result
                if(processingTime >= totalProcessingTime)
                {
                    this.onCompleteProcess();
                    this.setProcessingTime(0);
                }
            }
        }

        // Consume energy if always consuming
        if(this.requiresEnergy() && this.getEnergyMode() == EnergyMode.ALWAYS_CONSUME && this.getEnergy() > 0)
        {
            this.addEnergy(-1);
        }

        // If not processing, reset processing time back to zero
        if(!processing)
        {
            this.setProcessingTime(0);
        }
    }

    enum EnergyMode
    {
        /**
         * Always Consume mode is similar to a furance. It was continuously drain energy but only
         * add energy if it can process the input.
         */
        ALWAYS_CONSUME,

        /**
         * Only When Processing mode will only drain energy when processing the input. Energy is
         * only added if it can process the input.
         */
        ONLY_WHEN_PROCESSING
    }
}
