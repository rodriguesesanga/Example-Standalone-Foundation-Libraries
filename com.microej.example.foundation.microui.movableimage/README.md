# Overview

This example shows how to create and use movable images.

# Usage

## Run on MicroEJ Simulator

1. Right-click on the project
2. Select **Run as -> Run Configuration**
3. Right-click on **MicroEJ Application** configuration kind
4. Click on **New**
5. In **Execution** tab
    1. In **Target** frame, in **Platform** field, select a relevant platform (but not a virtual device)
    2. In **Execution** frame
        1. Select **Execute on Simulator**
6. In **Configuration** tab
    1. Go to **Libraries -> MicroUI**
        1. Set **Images heap size (in bytes)** to `275000`
7. Press **Apply**
8. Press **Run**

## Run on device

### Build

1. Right-click on the project
2. Select **Run as -> Run Configuration** 
3. Right-click on **MicroEJ Application** configuration kind
4. Click on **New**
5. In **Execution** tab
    1. In **Target** frame, in **Platform** field, select a relevant platform (but not a virtual device)
    2. In **Execution** frame
        1. Select **Execute on Device**
        2. In **Settings** field, select **Build & Deploy**
6. In **Configuration** tab
    1. Go to **Libraries -> MicroUI**
        1. Set **Images heap size (in bytes)** to `275000`
7. Press **Apply**
8. Press **Run**
9. The application file (`.o` or `.out`) has been generated

# Requirements

This example has been tested on:

* MicroEJ SDK 5.3
* With a platform that contains:
    * EDC-1.3
    * BON-1.4
    * MICROUI-3.0

## Dependencies

_All dependencies are retrieved transitively by Ivy resolver_.

# Source

N/A

# Restrictions

None.
 
---  
_Copyright 2016-2020 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  