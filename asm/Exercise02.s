			TTL Lab Exercise Two Basic Arithmetic Operations
		
;****************************************************************
;A basic PEMDAS Arithmetic Operation.
;Using numeric inputs, Calculate the result and store in
;register R0.
;Expression: -5 + 62 - (9/4) - (7*9) + 58 + 17
;Name:  Justin Peterson
;Date:  09/03/2015
;Class:  CMPE-250
;Section:  01L3 TR 2-4PM
;---------------------------------------------------------------
;Keil Simulator Template for KL46
;R. W. Melton
;January 23, 2015
;****************************************************************
;Assembler directives
            THUMB
            OPT    64  ;Turn on listing macro expansions
;****************************************************************
;EQUates
;Vectors
VECTOR_TABLE_SIZE EQU 0x000000C0
VECTOR_SIZE       EQU 4           ;Bytes per vector
	
;Shift Amounts
;****************************************************************
DIV4		  EQU 2			  ;Logical shift by 2 = 2^2 = 4
MULT8		  EQU 3			  ;Logical shift by 3 = 2^3 = 8
	
;Stack
SSTACK_SIZE EQU  0x00000100
;****************************************************************
;Program
;Linker requires Reset_Handler
            AREA    MyCode,CODE,READONLY
            ENTRY
            EXPORT  Reset_Handler
Reset_Handler
main
;---------------------------------------------------------------
;>>>>> begin main program code <<<<<
			
			;-5 + 62
			MOVS R0, #5	    ;Store 5
			RSBS R0, R0, #0 ;Invert 5 to -5
			MOVS R1, #62	;Store 62
			ADDS R0, R0, R1	;R0 = -5 + 62
			
			MOVS R1, #9		;Store 9
			ASRS R1, R1, #DIV4 ;R1 = 9/4 = 2
			
			;inst 1 - inst 2
			SUBS R1, R0, R1 ;R1 = 57 - 2 = 55
			
			;9 * 7 evaluated as 9*(2)^3 + 9
			MOVS R2, #7		;Store 9
			LSLS R2, R2, #MULT8 ;R2 = 7*(2)^3 = 56
			ADDS R2, R2, #7	;R2 = 56 + 7 = 63
			
			SUBS R0, R1, R2 ;R0 = 55 - 63
			
			MOVS R1, #58 	;Store 58 in R1
			MOVS R2, #17 	;Store 17 in R2
			ADDS R0, R0, R1 ;-8 + 58 = 50 
			ADDS R0, R0, R2 ;50 + 17 = 67 base 10
			
;>>>>>   end main program code <<<<<
;Stay here
            B       .
;---------------------------------------------------------------
;>>>>> begin subroutine code <<<<<
;>>>>>   end subroutine code <<<<<
            ALIGN
;****************************************************************
;Vector Table Mapped to Address 0 at Reset
;Linker requires __Vectors to be exported
            AREA    RESET, DATA, READONLY
            EXPORT  __Vectors
            EXPORT  __Vectors_End
            EXPORT  __Vectors_Size
__Vectors 
                                      ;ARM core vectors
            DCD    __initial_sp       ;00:end of stack
            DCD    Reset_Handler      ;reset vector
            SPACE  (VECTOR_TABLE_SIZE - (2 * VECTOR_SIZE))
__Vectors_End
__Vectors_Size  EQU     __Vectors_End - __Vectors
            ALIGN
;****************************************************************
;Constants
            AREA    MyConst,DATA,READONLY
;>>>>> begin constants here <<<<<
;>>>>>   end constants here <<<<<
;****************************************************************
            AREA    |.ARM.__at_0x1FFFE000|,DATA,READWRITE,ALIGN=3
            EXPORT  __initial_sp
;Allocate system stack
            IF      :LNOT::DEF:SSTACK_SIZE
SSTACK_SIZE EQU     0x00000100
            ENDIF
Stack_Mem   SPACE   SSTACK_SIZE
__initial_sp
;****************************************************************
;Variables
            AREA    MyData,DATA,READWRITE
;>>>>> begin variables here <<<<<
;>>>>>   end variables here <<<<<
            END