package com.jeanbarrossilva.tick.app.destination.groups

import com.jeanbarrossilva.tick.core.domain.group.ToDoGroup
import com.jeanbarrossilva.tick.std.selectable.SelectableList
import java.io.Serializable

/*
 * GroupsArgument has to be public because the GroupsDestination.invoke method generated by
 * compose-destinations is also public.
 */
class GroupsArgument internal constructor(val value: SelectableList<ToDoGroup>) : Serializable