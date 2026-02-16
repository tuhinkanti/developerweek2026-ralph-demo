#!/bin/bash
set -eo pipefail

MAX_ITERATIONS=${1:-10}
LOG_FILE="iteration.log"

# Validate gemini CLI is available
if ! command -v gemini &> /dev/null; then
    echo "Error: gemini CLI not found. Please install it first."
    exit 1
fi

# Validate we're in the right directory
if [ ! -f "prompt.md" ]; then
    echo "Error: prompt.md not found. Run this script from the ralph_demo directory."
    exit 1
fi

if [ ! -f "prd.json" ]; then
    echo "Error: prd.json not found."
    exit 1
fi

echo "Starting Ralph loop with max $MAX_ITERATIONS iterations..."
echo "==========================================="

for i in $(seq 1 $MAX_ITERATIONS); do
    echo ""
    echo ">>> Iteration $i of $MAX_ITERATIONS"
    echo "-------------------------------------------"
    
    cat prompt.md | gemini --yolo 2>&1 | tee "$LOG_FILE"
    
    if grep -q "<promise>COMPLETE</promise>" "$LOG_FILE"; then
        echo ""
        echo "==========================================="
        echo "All stories complete!"
        rm -f "$LOG_FILE"
        exit 0
    fi
    
    sleep 2
done

rm -f "$LOG_FILE"

echo ""
echo "==========================================="
echo "Max iterations reached. Check prd.json for remaining stories."
exit 1
