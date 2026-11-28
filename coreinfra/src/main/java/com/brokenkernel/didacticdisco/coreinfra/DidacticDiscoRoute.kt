package com.brokenkernel.didacticdisco.coreinfra

import android.os.Parcelable
import androidx.compose.runtime.snapshots.SnapshotStateList

public interface DidacticDiscoRoute : Parcelable

public typealias BackStack = SnapshotStateList<DidacticDiscoRoute>
