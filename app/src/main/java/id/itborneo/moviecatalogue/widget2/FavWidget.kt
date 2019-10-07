package id.itborneo.moviecatalogue.widget2

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import id.itborneo.moviecatalogue.R


class FavWidget : AppWidgetProvider() {
    private val TAG = "FavWidget"
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        
        for (appWidgetId in appWidgetIds) {
            Log.d(TAG,"onUpdate called: appwidgetId is $appWidgetId")
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
    
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if(intent?.action !=null){
            if(intent.action == TOAST_ACTION){
                val viewIndex = intent.getIntExtra(EXTRA_ITEM, 0)
                Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
    
        private const val TOAST_ACTION  = "id.itborneo.moviecatalogue.TOAST_ACTION"
        const val EXTRA_ITEM = "id.it.borneo.moviecatalogue.EXTRA_ITEM"
        
        
        private fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            
            val intent  = Intent(context,FavStackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            
            
            val views = RemoteViews(context.packageName, R.layout.fav_widget)
            views.setRemoteAdapter(R.id.stack_view,intent )
            
            //set when data is empty
            views.setEmptyView(R.id.stack_view, R.id.empty_view)
            
            val toastIntent = Intent(context, FavWidget::class.java)
            toastIntent.action= TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val toastPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)
            
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

