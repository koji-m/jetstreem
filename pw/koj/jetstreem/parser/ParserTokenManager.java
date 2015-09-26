/* Generated By:JavaCC: Do not edit this line. ParserTokenManager.java */
package pw.koj.jetstreem.parser;
import java.lang.reflect.*;
import java.util.*;
import java.io.*;
import pw.koj.jetstreem.ast.*;
import pw.koj.jetstreem.type.*;

/** Token Manager. */
public class ParserTokenManager implements ParserConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 10:
         return jjStartNfaWithStates_0(0, 40, 106);
      case 33:
         return jjStartNfaWithStates_0(0, 42, 45);
      case 34:
         return jjStopAtPos(0, 35);
      case 41:
         return jjStopAtPos(0, 44);
      case 58:
         return jjStopAtPos(0, 46);
      case 61:
         return jjStartNfaWithStates_0(0, 41, 42);
      case 93:
         return jjStopAtPos(0, 45);
      case 125:
         return jjStopAtPos(0, 47);
      case 126:
         return jjStopAtPos(0, 43);
      default :
         return jjMoveNfa_0(3, 0);
   }
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 106;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 106:
                  if ((0x100000600L & l) != 0L)
                     jjCheckNAddTwoStates(89, 68);
                  else if (curChar == 46)
                  {
                     if (kind > 27)
                        kind = 27;
                     jjCheckNAdd(69);
                  }
                  if ((0x100000600L & l) != 0L)
                     jjCheckNAddTwoStates(83, 88);
                  break;
               case 3:
                  if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 33)
                        kind = 33;
                     jjCheckNAddStates(0, 2);
                  }
                  else if ((0x100003200L & l) != 0L)
                  {
                     if (kind > 1)
                        kind = 1;
                     jjCheckNAdd(0);
                  }
                  else if (curChar == 48)
                  {
                     if (kind > 33)
                        kind = 33;
                     jjCheckNAdd(103);
                  }
                  else if (curChar == 60)
                     jjAddStates(3, 4);
                  else if (curChar == 59)
                  {
                     if (kind > 32)
                        kind = 32;
                     jjCheckNAdd(79);
                  }
                  else if (curChar == 44)
                  {
                     if (kind > 31)
                        kind = 31;
                     jjCheckNAdd(77);
                  }
                  else if (curChar == 40)
                  {
                     if (kind > 28)
                        kind = 28;
                     jjCheckNAdd(71);
                  }
                  else if (curChar == 46)
                  {
                     if (kind > 27)
                        kind = 27;
                     jjCheckNAdd(69);
                  }
                  else if (curChar == 45)
                     jjstateSet[jjnewStateCnt++] = 65;
                  else if (curChar == 38)
                  {
                     if (kind > 24)
                        kind = 24;
                     jjCheckNAdd(64);
                  }
                  else if (curChar == 62)
                     jjstateSet[jjnewStateCnt++] = 52;
                  else if (curChar == 33)
                     jjstateSet[jjnewStateCnt++] = 45;
                  else if (curChar == 61)
                     jjstateSet[jjnewStateCnt++] = 42;
                  else if (curChar == 37)
                  {
                     if (kind > 14)
                        kind = 14;
                     jjCheckNAdd(41);
                  }
                  else if (curChar == 47)
                  {
                     if (kind > 13)
                        kind = 13;
                     jjCheckNAdd(39);
                  }
                  else if (curChar == 42)
                  {
                     if (kind > 12)
                        kind = 12;
                     jjCheckNAdd(37);
                  }
                  else if (curChar == 43)
                  {
                     if (kind > 10)
                        kind = 10;
                     jjCheckNAdd(33);
                  }
                  if ((0x100000600L & l) != 0L)
                     jjCheckNAddStates(5, 8);
                  else if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 55;
                  else if (curChar == 62)
                  {
                     if (kind > 19)
                        kind = 19;
                     jjCheckNAdd(51);
                  }
                  else if (curChar == 60)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAdd(49);
                  }
                  else if (curChar == 45)
                  {
                     if (kind > 11)
                        kind = 11;
                     jjCheckNAdd(35);
                  }
                  break;
               case 0:
                  if ((0x100003200L & l) == 0L)
                     break;
                  if (kind > 1)
                     kind = 1;
                  jjCheckNAdd(0);
                  break;
               case 2:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 2)
                     kind = 2;
                  jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 5:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 4)
                     kind = 4;
                  jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 11:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 6)
                     kind = 6;
                  jjstateSet[jjnewStateCnt++] = 11;
                  break;
               case 18:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 7)
                     kind = 7;
                  jjstateSet[jjnewStateCnt++] = 18;
                  break;
               case 22:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 8)
                     kind = 8;
                  jjstateSet[jjnewStateCnt++] = 22;
                  break;
               case 27:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 9)
                     kind = 9;
                  jjstateSet[jjnewStateCnt++] = 27;
                  break;
               case 32:
                  if (curChar != 43)
                     break;
                  kind = 10;
                  jjCheckNAdd(33);
                  break;
               case 33:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 10)
                     kind = 10;
                  jjCheckNAdd(33);
                  break;
               case 34:
                  if (curChar != 45)
                     break;
                  if (kind > 11)
                     kind = 11;
                  jjCheckNAdd(35);
                  break;
               case 35:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 11)
                     kind = 11;
                  jjCheckNAdd(35);
                  break;
               case 36:
                  if (curChar != 42)
                     break;
                  kind = 12;
                  jjCheckNAdd(37);
                  break;
               case 37:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 12)
                     kind = 12;
                  jjCheckNAdd(37);
                  break;
               case 38:
                  if (curChar != 47)
                     break;
                  kind = 13;
                  jjCheckNAdd(39);
                  break;
               case 39:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 13)
                     kind = 13;
                  jjCheckNAdd(39);
                  break;
               case 40:
                  if (curChar != 37)
                     break;
                  kind = 14;
                  jjCheckNAdd(41);
                  break;
               case 41:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 14)
                     kind = 14;
                  jjCheckNAdd(41);
                  break;
               case 42:
                  if (curChar != 61)
                     break;
                  if (kind > 15)
                     kind = 15;
                  jjCheckNAdd(43);
                  break;
               case 43:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 15)
                     kind = 15;
                  jjCheckNAdd(43);
                  break;
               case 44:
                  if (curChar == 61)
                     jjstateSet[jjnewStateCnt++] = 42;
                  break;
               case 45:
                  if (curChar != 61)
                     break;
                  if (kind > 16)
                     kind = 16;
                  jjCheckNAdd(46);
                  break;
               case 46:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 16)
                     kind = 16;
                  jjCheckNAdd(46);
                  break;
               case 47:
                  if (curChar == 33)
                     jjstateSet[jjnewStateCnt++] = 45;
                  break;
               case 48:
                  if (curChar != 60)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAdd(49);
                  break;
               case 49:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAdd(49);
                  break;
               case 50:
                  if (curChar != 62)
                     break;
                  if (kind > 19)
                     kind = 19;
                  jjCheckNAdd(51);
                  break;
               case 51:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 19)
                     kind = 19;
                  jjCheckNAdd(51);
                  break;
               case 52:
                  if (curChar != 61)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(53);
                  break;
               case 53:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(53);
                  break;
               case 54:
                  if (curChar == 62)
                     jjstateSet[jjnewStateCnt++] = 52;
                  break;
               case 55:
                  if (curChar != 38)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAdd(56);
                  break;
               case 56:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAdd(56);
                  break;
               case 57:
                  if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 55;
                  break;
               case 59:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 22)
                     kind = 22;
                  jjstateSet[jjnewStateCnt++] = 59;
                  break;
               case 62:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 23)
                     kind = 23;
                  jjstateSet[jjnewStateCnt++] = 62;
                  break;
               case 63:
                  if (curChar != 38)
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAdd(64);
                  break;
               case 64:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAdd(64);
                  break;
               case 65:
                  if (curChar != 62)
                     break;
                  if (kind > 26)
                     kind = 26;
                  jjCheckNAdd(66);
                  break;
               case 66:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 26)
                     kind = 26;
                  jjCheckNAdd(66);
                  break;
               case 67:
                  if (curChar == 45)
                     jjstateSet[jjnewStateCnt++] = 65;
                  break;
               case 68:
                  if (curChar != 46)
                     break;
                  if (kind > 27)
                     kind = 27;
                  jjCheckNAdd(69);
                  break;
               case 69:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 27)
                     kind = 27;
                  jjCheckNAdd(69);
                  break;
               case 70:
                  if (curChar != 40)
                     break;
                  kind = 28;
                  jjCheckNAdd(71);
                  break;
               case 71:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 28)
                     kind = 28;
                  jjCheckNAdd(71);
                  break;
               case 73:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjstateSet[jjnewStateCnt++] = 73;
                  break;
               case 75:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 30)
                     kind = 30;
                  jjstateSet[jjnewStateCnt++] = 75;
                  break;
               case 76:
                  if (curChar != 44)
                     break;
                  kind = 31;
                  jjCheckNAdd(77);
                  break;
               case 77:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 31)
                     kind = 31;
                  jjCheckNAdd(77);
                  break;
               case 78:
                  if (curChar != 59)
                     break;
                  kind = 32;
                  jjCheckNAdd(79);
                  break;
               case 79:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 32)
                     kind = 32;
                  jjCheckNAdd(79);
                  break;
               case 81:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 39)
                     kind = 39;
                  jjstateSet[jjnewStateCnt++] = 81;
                  break;
               case 82:
                  if ((0x100000600L & l) != 0L)
                     jjCheckNAddStates(5, 8);
                  break;
               case 83:
                  if ((0x100000600L & l) != 0L)
                     jjCheckNAddTwoStates(83, 88);
                  break;
               case 85:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjstateSet[jjnewStateCnt++] = 85;
                  break;
               case 89:
                  if ((0x100000600L & l) != 0L)
                     jjCheckNAddTwoStates(89, 68);
                  break;
               case 92:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 5)
                     kind = 5;
                  jjstateSet[jjnewStateCnt++] = 92;
                  break;
               case 95:
                  if (curChar == 60)
                     jjAddStates(3, 4);
                  break;
               case 96:
                  if (curChar != 61)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjCheckNAdd(97);
                  break;
               case 97:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjCheckNAdd(97);
                  break;
               case 98:
                  if (curChar != 45)
                     break;
                  if (kind > 25)
                     kind = 25;
                  jjCheckNAdd(99);
                  break;
               case 99:
                  if ((0x100000600L & l) == 0L)
                     break;
                  if (kind > 25)
                     kind = 25;
                  jjCheckNAdd(99);
                  break;
               case 100:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 33)
                     kind = 33;
                  jjCheckNAddStates(0, 2);
                  break;
               case 101:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 33)
                     kind = 33;
                  jjCheckNAdd(101);
                  break;
               case 102:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(102, 103);
                  break;
               case 103:
                  if (curChar != 46)
                     break;
                  if (kind > 34)
                     kind = 34;
                  jjCheckNAdd(104);
                  break;
               case 104:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 34)
                     kind = 34;
                  jjCheckNAdd(104);
                  break;
               case 105:
                  if (curChar != 48)
                     break;
                  if (kind > 33)
                     kind = 33;
                  jjCheckNAdd(103);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 106:
               case 88:
                  if (curChar == 101)
                     jjCheckNAdd(87);
                  break;
               case 3:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 39)
                        kind = 39;
                     jjCheckNAdd(81);
                  }
                  else if (curChar == 123)
                  {
                     if (kind > 30)
                        kind = 30;
                     jjstateSet[jjnewStateCnt++] = 75;
                  }
                  else if (curChar == 91)
                  {
                     if (kind > 29)
                        kind = 29;
                     jjstateSet[jjnewStateCnt++] = 73;
                  }
                  else if (curChar == 124)
                  {
                     if (kind > 23)
                        kind = 23;
                     jjstateSet[jjnewStateCnt++] = 62;
                  }
                  if (curChar == 101)
                     jjCheckNAddTwoStates(87, 94);
                  else if (curChar == 124)
                     jjstateSet[jjnewStateCnt++] = 58;
                  else if (curChar == 102)
                     jjstateSet[jjnewStateCnt++] = 30;
                  else if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 24;
                  else if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 19;
                  else if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 15;
                  else if (curChar == 98)
                     jjstateSet[jjnewStateCnt++] = 8;
                  else if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 1:
                  if (curChar != 102)
                     break;
                  if (kind > 2)
                     kind = 2;
                  jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 4:
                  if (curChar != 107)
                     break;
                  if (kind > 4)
                     kind = 4;
                  jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 6:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 7:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 8:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 9:
                  if (curChar == 98)
                     jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 10:
                  if (curChar != 110)
                     break;
                  if (kind > 6)
                     kind = 6;
                  jjstateSet[jjnewStateCnt++] = 11;
                  break;
               case 12:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 13:
                  if (curChar == 117)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 14:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 13;
                  break;
               case 15:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 14;
                  break;
               case 16:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 15;
                  break;
               case 17:
                  if (curChar != 108)
                     break;
                  if (kind > 7)
                     kind = 7;
                  jjstateSet[jjnewStateCnt++] = 18;
                  break;
               case 19:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 17;
                  break;
               case 20:
                  if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 19;
                  break;
               case 21:
                  if (curChar != 101)
                     break;
                  if (kind > 8)
                     kind = 8;
                  jjstateSet[jjnewStateCnt++] = 22;
                  break;
               case 23:
                  if (curChar == 117)
                     jjstateSet[jjnewStateCnt++] = 21;
                  break;
               case 24:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 23;
                  break;
               case 25:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 24;
                  break;
               case 26:
                  if (curChar != 101)
                     break;
                  if (kind > 9)
                     kind = 9;
                  jjstateSet[jjnewStateCnt++] = 27;
                  break;
               case 28:
                  if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 26;
                  break;
               case 29:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 28;
                  break;
               case 30:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 29;
                  break;
               case 31:
                  if (curChar == 102)
                     jjstateSet[jjnewStateCnt++] = 30;
                  break;
               case 58:
                  if (curChar != 124)
                     break;
                  if (kind > 22)
                     kind = 22;
                  jjstateSet[jjnewStateCnt++] = 59;
                  break;
               case 60:
                  if (curChar == 124)
                     jjstateSet[jjnewStateCnt++] = 58;
                  break;
               case 61:
                  if (curChar != 124)
                     break;
                  if (kind > 23)
                     kind = 23;
                  jjstateSet[jjnewStateCnt++] = 62;
                  break;
               case 72:
                  if (curChar != 91)
                     break;
                  kind = 29;
                  jjstateSet[jjnewStateCnt++] = 73;
                  break;
               case 74:
                  if (curChar != 123)
                     break;
                  kind = 30;
                  jjstateSet[jjnewStateCnt++] = 75;
                  break;
               case 80:
               case 81:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 39)
                     kind = 39;
                  jjCheckNAdd(81);
                  break;
               case 84:
                  if (curChar != 101)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjstateSet[jjnewStateCnt++] = 85;
                  break;
               case 86:
                  if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 84;
                  break;
               case 87:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 86;
                  break;
               case 90:
                  if (curChar == 101)
                     jjCheckNAddTwoStates(87, 94);
                  break;
               case 91:
                  if (curChar != 116)
                     break;
                  if (kind > 5)
                     kind = 5;
                  jjstateSet[jjnewStateCnt++] = 92;
                  break;
               case 93:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 91;
                  break;
               case 94:
                  if (curChar == 109)
                     jjstateSet[jjnewStateCnt++] = 93;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (int)(curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 106 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private final int jjStopStringLiteralDfa_1(int pos, long active0)
{
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_1(int pos, long active0)
{
   return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
}
private int jjMoveStringLiteralDfa0_1()
{
   switch(curChar)
   {
      case 34:
         return jjStopAtPos(0, 38);
      default :
         return jjMoveNfa_1(1, 0);
   }
}
static final long[] jjbitVec0 = {
   0xfffffffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static final long[] jjbitVec2 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_1(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 3;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
               case 0:
                  if ((0xfffffffbffffdbffL & l) == 0L)
                     break;
                  if (kind > 36)
                     kind = 36;
                  jjCheckNAdd(0);
                  break;
               case 2:
                  if (kind > 37)
                     kind = 37;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  if ((0xffffffffefffffffL & l) != 0L)
                  {
                     if (kind > 36)
                        kind = 36;
                     jjCheckNAdd(0);
                  }
                  else if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 0:
                  if ((0xffffffffefffffffL & l) == 0L)
                     break;
                  if (kind > 36)
                     kind = 36;
                  jjCheckNAdd(0);
                  break;
               case 2:
                  if (kind > 37)
                     kind = 37;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (int)(curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
               case 0:
                  if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 36)
                     kind = 36;
                  jjCheckNAdd(0);
                  break;
               case 2:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 37)
                     kind = 37;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 3 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   101, 102, 103, 96, 98, 83, 88, 89, 68, 
};
private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 0:
         return ((jjbitVec2[i2] & l2) != 0L);
      default :
         if ((jjbitVec0[i1] & l1) != 0L)
            return true;
         return false;
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, "\12", 
"\75", "\41", "\176", "\51", "\135", "\72", "\175", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
   "INSIDE_STRING",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1, -1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0xffc7fffffffdL, 
};
static final long[] jjtoSkip = {
   0x2L, 
};
static final long[] jjtoSpecial = {
   0x2L, 
};
static final long[] jjtoMore = {
   0x3800000000L, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[106];
private final int[] jjstateSet = new int[212];
protected char curChar;
/** Constructor. */
public ParserTokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public ParserTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 106; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 2 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      matchedToken.specialToken = specialToken;
      return matchedToken;
   }

   for (;;)
   {
     switch(curLexState)
     {
       case 0:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_0();
         break;
       case 1:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_1();
         break;
     }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
           matchedToken.specialToken = specialToken;
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else if ((jjtoSkip[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
           {
              matchedToken = jjFillToken();
              if (specialToken == null)
                 specialToken = matchedToken;
              else
              {
                 matchedToken.specialToken = specialToken;
                 specialToken = (specialToken.next = matchedToken);
              }
           }
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
      if (jjnewLexState[jjmatchedKind] != -1)
        curLexState = jjnewLexState[jjmatchedKind];
        curPos = 0;
        jjmatchedKind = 0x7fffffff;
        try {
           curChar = input_stream.readChar();
           continue;
        }
        catch (java.io.IOException e1) { }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
   }
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}
