# Message Transmitter
 Project simulating real world message transmission.

## Project Description

The first SMS in history was sent on December 3, 1993. Since then, the vast majority of
mobile devices have the ability to send messages of this type. The result of this project
will be an application simulating the transmission of messages from the sender (mobile
device) through a series of network layers (intermediate stations) to the recipients (mobile
devices).

Using the SWING framework, create a multithreaded window application simulating
the transmission of SMS messages from senders to recipients. The visual layer of the
application will consist of 3 interactive panels (objects inheriting from the JPanel class),
arranged by the automatic BorderLayout layout.
The extreme left panel will be dedicated to displaying sending devices. This panel will
consist of a JScrollPane component placed in the central part and a JButton "Add" located
at the bottom of the panel. Inside the JScrollPane component, a JPanel will be placed,
which will use a vertical BoxLayout to arrange all visual components representing "virtual
sending devices" (VBD).

Pressing the "Add" button in the sending devices panel will display a dialog box allowing the user to enter and confirm a short text message. Based on the entered message,
an object implementing a virtual sending device (VBD) will be created, whose instances
will be visualized in the panel.

The extreme right panel will be dedicated to displaying receiving devices. The structure of this panel will be analogous to the structure of the sending devices panel. However,
it will present virtual receiving devices (VRD), whose objects will be created as a result
of pressing the "Add" button.

The middle panel will allow the visualization of base stations (BTS) in the extreme
layers and controller stations (BSC) in the intermediate layers. This panel will consist of
a JPanel component placed in the central part, containing horizontally oriented visualizations of BTS and BCS layers, and buttons for adding and removing BSC layers at the
bottom.

## Application operation

The user of the application creates a certain number of VBD objects, each of which is a
separate thread and immediately starts transmitting the message entered during creation.
As according to the standard, each SMS has the encoded sender and recipient numbers
along with the message, a random VRD element is chosen as the recipient. The created
SMS is passed to the BTS station with the least number of waiting SMSs.

The application in its running state has three layers. There must be at least one
intermediate layer of BSC controllers between the input layer and the output layer. The
number of intermediate layers depends on the user’s actions in the application, who can
add or remove a layer using the keys. Each newly created communication layer will be
created with one BSC, while removing a layer will result in no longer accepting messages
by that layer and immediately passing messages from all BSCs, skipping the transmission
timers.

The transmission of an example SMS message will look as follows:
 VBD → BTS → BSC → · · · → BTS → VRD

