# clusteriser
Clusteriser divides input .fasta file into small groups that are more appropriate to analysis.

### What is the program input?

FASTA sequence alignment

### What is the program output?

**timestamp-folder** with FASTA alignments. One file per cluster. {fs|ws|we}_{cluster id} - full sized, ws - without start, we - without end.
 
### Parameters

* -h | --help 
* -g | --gap - number of sequences which will be put into 'without beginning' or 'without end'. Need both parameters if used.
* -p | --percent - if used, the previous parameter is used in percents. By default, uses number of chars.
* -c | --coeff - similarity coeff. The minimal threshold for sequences to be in the same group.
* -f | --file - path to the input FASTA alignment
* -r | --register - if used, lowercase letters are considered to be gaps.
* -a | --alphabet <alphabet name> - which reduced alphabet to use:
    * SE_B_14
    * SE_B_10
    * SE_V_10
    * LI_A_10
    * LI_B_10
    * SOLIS_D_10
    * SOLIS_G_10
    * MURPHY_19
    * SE_B_8
    * SE_B_6
    * DAYHOFF_6
    * REGULAR (non-reduced)
    * <absolute file path> (groups, separated by comma, e.g.: QWER,SD,F. Case insensitive.). In case there is a bug in alphabet description, the REGULAR is used.

Only **-f** is an obligatory parameter.

### Default parameter values

* -g = 50 50 
* -c = 0.4
* -r = case insensitive by default
* -a = REGULAR

### Example run
java -jar clusteriser.jar -g 10 10 -c 0.5 -f testdata.fasta

After the program has finished the job, the *result* folder is created. Inside there is a folder with timestamp where the results are.
