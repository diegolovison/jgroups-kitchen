Lab ChatDemo: writing a simple chat application
-----------------------------------------------
. Create a channel
. Register a ReceiverAdapter with it
.. Method `receive()` prints the received chat message
.. Method `viewAccepted()` prints the view
. Connect the channel
. In a loop
.. Read a line from stdin (`Util.readLine(System.in)`) and
.. Send a message to all cluster nodes
. Extra credits
.. Store the last N messages in memory as state and perform a state transfer on startup
.. Expose contents and length of state via JMX
.. Make N configurable via a property "max_history"